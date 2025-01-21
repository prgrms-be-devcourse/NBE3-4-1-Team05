package com.team5.nbe341team05.domain.menu.service;

import com.team5.nbe341team05.common.exception.ServiceException;
import com.team5.nbe341team05.domain.menu.dto.MenuRequestDto;
import com.team5.nbe341team05.domain.menu.entity.Menu;
import com.team5.nbe341team05.domain.menu.repository.MenuRepository;
import com.team5.nbe341team05.domain.menu.type.MenuSortType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private static final int PAGE_SIZE = 10; // 한 페이지당 보여줄 메뉴 개수

    @Transactional
    public Page<Menu> getAllMenus(int page, MenuSortType sortType) {
        // 조회순, 최근등록순, 나중등록순, 가격높은순, 가격낮은순
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(sortType.getOrder());

        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(sorts));
        return menuRepository.findAll(pageable);
    }

    @Transactional
    public Menu getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 메뉴를 찾을 수 없습니다. id : " + id));
        menu.plusView();
        return menu;
    }

    public long count() {
        return menuRepository.count();
    }

    @SneakyThrows
    @Transactional
    public Menu updateMenu(long id, MenuRequestDto menuRequestDto, MultipartFile image) throws IOException {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new ServiceException("404", "해당 상품을 찾을 수 없습니다."));
        String imagePath = saveImage(image);
        menu.update(
                menuRequestDto.getProductName(),
                menuRequestDto.getPrice(),
                menuRequestDto.getStock(),
                imagePath);
        return menu;
    }
    @Transactional
    public Menu create(MenuRequestDto menuRequestDto, MultipartFile image) throws IOException {

        String imagePath = saveImage(image);

        Menu menu = Menu.builder()
                .productName(menuRequestDto.getProductName())
                .description(menuRequestDto.getDescription())
                .price(menuRequestDto.getPrice())
                .stock(menuRequestDto.getStock())
                .image(imagePath)
                .views(0)
                .build();

        menuRepository.save(menu);

        return menu;
    }

    @Transactional
    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new ServiceException("404","해당 상품을 찾을 수 없습니다."));
        menuRepository.delete(menu);
    }

    @Value("${upload.path}")
    private String uploadDirPath;


    @Transactional
    @SneakyThrows
    public String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return null; // 이미지가 없을 경우 null 처리
        }

        String uploadDir = Paths.get(uploadDirPath).toAbsolutePath().toString();;
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리 없으면 생성
        }

        String originalFilename = imageFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        Path filePath = Paths.get(uploadDir, originalFilename);
        imageFile.transferTo(filePath.toFile());

        return originalFilename;
    }
}

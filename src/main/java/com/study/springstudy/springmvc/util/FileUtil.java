package com.study.springstudy.springmvc.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FileUtil {
    /**
     * 사용자가 클라이언트에서 파일을 전송했을 때
     * 중복이 없는 새로운 파일명을 생성하여 해당 파일명으로
     * 날짜별 폴더로 업로드하는 메서드
     *
     * @param file - 사용자가 업로드한 파일의 정보객체
     * @param rootPath - 서버에 업로드할 루트 디렉토리 경로
     *                   ex) C:/spring-prj/upload
     * @return - 업로드가 완료되었을 경우 업로드 된 파일의 위치 경로
     *                   ex)  /2024/06/05/djfalsjdflaksjlfdsaj_고양이.jpg
     */
    public static String uploadFile(String rootPath , MultipartFile file){

        // 1. 원본 파일명을 중복이 없는 랜덤 파일명으로 변경
        String newFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // 이 첨부파일을 날짜별로 관리하기 위해 날짜 폴더를 생성
        String newUploadPath = makeDateFormatDirectory(rootPath);

        // 파일 업로드 수행
        try {
            file.transferTo(new File(newUploadPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 업로드가 완료되면 데이터베이스에 파일의 경로 위치를 저장한다.
        // 확장자 필수 , 파일 경로 코드작성란에 띄어쓰기 절대 금지
        // ex) ocal/2024/06/05/20c1399b-1522-4ab5-a84b-18e0aac15776_shutterstock_2205178589-1-1.png

        // 파일 전체 경로
        // fullPath : C:/spring_pr/upload/2024/06/05/20c1399b-1522-4ab5-a84b-18e0aac15776_shutterstock_2205178589-1-1.png
        String fullPath = newUploadPath +"/" + newFileName;

        // url-path : local/2024/06/05/20c1399b-1522-4ab5-a84b-18e0aac15776_shutterstock_2205178589-1-1.png
        String urlPath = "/local" + fullPath.substring(rootPath.length());

        return urlPath;
    }

    private static String makeDateFormatDirectory(String rootPath) {

        //  오늘 날짜 정보를 추출
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        List<String> dateList = List.of(year + "",len2(month),len2(day));

        // rootPath - C:/spring-prj/upload
        String newDirectoryPath = rootPath;
        for (String s : dateList) {
            newDirectoryPath += "/" + s;
            File f = new File(newDirectoryPath);
            if(!f.exists())f.mkdir();
        }

        return newDirectoryPath;
    }

    private static String len2(int n){
        return new DecimalFormat("00").format(n);
    }

}

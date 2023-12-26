package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController { //수정

   // 업로드 폼 페이지로 이동
   @GetMapping("/uploadForm")
   public void uploadForm() {

      log.info("upload form");
   }

   // 파일을 업로드하는 페이지에서 전송된 파일 처리
   @PostMapping("/uploadFormAction")
   public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
      
      //파일저장 경로
      String uploadFolder = "C:\\upload";
      
      //첨부된 파일 등록
      for (MultipartFile multipartFile : uploadFile) {
         // 파일 이름과 크기 출력
         log.info("-------------------------------------");
         log.info("Upload File Name: " + multipartFile.getOriginalFilename());
         log.info("Upload File Size: " + multipartFile.getSize());
         
         //업로드된 파일 실제 파일 이름 반환하여 저장 
         File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());

         try {
        	//파일 저장
            multipartFile.transferTo(saveFile);
         } catch (Exception e) {
            log.error(e.getMessage());
         } // end catch
      } // end for

   }
   
   // 업로드 페이지로 이동 (Ajax 방식)
   @PreAuthorize("isAuthenticated()")
   @GetMapping("/uploadAjax")
   public void uploadAjax() {

      log.info("upload ajax");
   }
   //파일 경로를 오늘 날짜로 하기 위한 메서드 
   private String getFolder() {

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

      Date date = new Date();

      String str = sdf.format(date);
      //날짜 - 를 파일 경로 구분자로 변경
      return str.replace("-", File.separator);
   }

   //첨부파일이 이미지일 때만 true 반환
   private boolean checkImageType(File file) {

      try {
         String contentType = Files.probeContentType(file.toPath());

         return contentType.startsWith("image");

      } catch (IOException e) {
         e.printStackTrace();
      }

      return false;
   }

   // Ajax를 통해 파일 업로드 처리
   @PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @ResponseBody
   public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {

      List<AttachFileDTO> list = new ArrayList<>();
      String uploadFolder = "C:\\upload";

      String uploadFolderPath = getFolder();
      // make folder --------
      File uploadPath = new File(uploadFolder, uploadFolderPath);
      //해당 폴더가 존재하지 않을 시 폴더 생성
      if (uploadPath.exists() == false) {
         uploadPath.mkdirs();
      }
      // make yyyy/MM/dd folder

      for (MultipartFile multipartFile : uploadFile) {

         AttachFileDTO attachDTO = new AttachFileDTO();

         String uploadFileName = multipartFile.getOriginalFilename();

         // IE has file path
         uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
         log.info("only file name: " + uploadFileName);
         attachDTO.setFileName(uploadFileName);

         // 중복방지를 위해 새로운 파일 이름 랜덤 생성
         UUID uuid = UUID.randomUUID();

         uploadFileName = uuid.toString() + "_" + uploadFileName;

         try {
            File saveFile = new File(uploadPath, uploadFileName);
            multipartFile.transferTo(saveFile);

            attachDTO.setUuid(uuid.toString());
            attachDTO.setUploadPath(uploadFolderPath);

            // check image type file
            if (checkImageType(saveFile)) {
            	
            	//이미지 파일로 설정
               attachDTO.setImage(true);
               //썸네일 생성
               FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));

               Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);

               thumbnail.close();
            }

            // add to List
            list.add(attachDTO);

         } catch (Exception e) {
            e.printStackTrace();
         }

      } // end for 						//OK(200, "OK") 메서드로 에러없이 전송 성공여부 나타냄
      return new ResponseEntity<>(list, HttpStatus.OK);
   }

   // 파일 보기
   @GetMapping("/display")
   @ResponseBody
   public ResponseEntity<byte[]> getFile(String fileName) {

      log.info("fileName: " + fileName);

      File file = new File("c:\\upload\\" + fileName);

      log.info("file: " + file);
      
      //byte 배열로 받음
      ResponseEntity<byte[]> result = null;

      try {
         HttpHeaders header = new HttpHeaders();

         header.add("Content-Type", Files.probeContentType(file.toPath()));
         result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
         								//byte배열, content-type 헤더 설정하여 MIME 타입 나타냄, 상태코드로 성공 여부 나타냄
      } catch (IOException e) {
         e.printStackTrace();
      }
      return result;
   }

   // 파일 다운로드 (브라우저에 따라 헤더 처리)
   @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
   @ResponseBody
   public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {

	   //해당파일을 읽어오기 위한 객체 생성
      Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
      
      if (resource.exists() == false) { //파일존재여부 확인 - 없으면 404 error!!!
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      String resourceName = resource.getFilename();

      // remove UUID
      String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);

      HttpHeaders headers = new HttpHeaders();
      try {

         boolean checkIE = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);

         String downloadName = null;

         if (checkIE) {
            downloadName = URLEncoder.encode(resourceOriginalName, "UTF8").replaceAll("\\+", " ");
         } else {
            downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
         }

         headers.add("Content-Disposition", "attachment; filename=" + downloadName);

      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      }

      return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
   }
   
   // 게시글 작성시 첨부파일 등록후 x버튼으로 삭제
   @PreAuthorize("isAuthenticated()")
   @PostMapping("/deleteFile")
   @ResponseBody
   public ResponseEntity<String> deleteFile(String fileName, String type) {

      log.info("deleteFile: " + fileName);

      File file;

      try {
         file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));

         file.delete();

         if (type.equals("image")) {

        	 //이미지 파일인 경우 썸네일 삭제
            String largeFileName = file.getAbsolutePath().replace("s_", "");

            log.info("largeFileName: " + largeFileName);

            file = new File(largeFileName);

            file.delete();
         }

      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      //삭제하면 deleted 메세지와 함께 HttpStatus.OK 반환
      return new ResponseEntity<String>("deleted", HttpStatus.OK);

   }
   

}
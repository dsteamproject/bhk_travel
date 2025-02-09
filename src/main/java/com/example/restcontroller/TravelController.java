package com.example.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;

import com.example.entity.City;
import com.example.entity.Good;
import com.example.entity.Member;
import com.example.entity.TD;
import com.example.entity.TDImg;
import com.example.entity.TDSave;
import com.example.jwt.JwtUtil;
import com.example.repository.CityRepository;
import com.example.repository.GoodRepository;
import com.example.repository.MemberRepository;
import com.example.repository.TDRepository;
import com.example.repository.TDSaveRepository;
import com.example.repository.TDimgRepository;
import com.example.repository.TypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/travel")
public class TravelController {

    @Value("${default.image}")
    private String DEFAULTIMAGE;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    GoodRepository goodRepository;

    @Autowired
    TDSaveRepository tdsaveRepository;

    @Autowired
    MemberRepository mRepository;

    @Autowired
    TDRepository tdRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    TDimgRepository tdimgRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // 127.0.0.1:8080/REST/travel/image1
    @GetMapping(value = "/image1", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/image/1.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/image2", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType1() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/image/2.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/image3", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType2() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/image/3.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/image4", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType3() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/image/4.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/image5", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType4() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/image/5.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/image6", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType5() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/image/6.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/image7", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType6() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/image/7.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/image8", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType7() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/image/8.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/image9", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType8() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/image/9.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/image10", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType9() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/image/10.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/image11", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType10() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/image/11.png");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/image12", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType11() throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/image/12.png");
        return IOUtils.toByteArray(in);
    }

    // DB저장된 지역코드(City, Sigungu) 조회
    // Param : size(표시할 갯수) , page(페이지넘버) , areacode("" : City , "number" : Sigungu)
    // Return : Citylist or Sigungulist
    @GetMapping(value = "/city")
    public Map<String, Object> gettourapicity(
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "areacode", defaultValue = "") String areacode) {
        Map<String, Object> map = new HashMap<>();
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, size);
            List<City> citylist = cityRepository.findAll(pageRequest).toList();
            if (areacode.equals(""))
                map.put("Citylist", citylist);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;

    }

    // 지역, contenttype별 목록
    // Param : areacode, contenttypeId, page
    @GetMapping(value = "/select")
    public Map<String, Object> getTourApiselect(@RequestHeader(name = "TOKEN", required = false) String token,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "title", defaultValue = "") String title,
            @RequestParam(name = "contentTypeId", defaultValue = "") String contentTypeId,
            @RequestParam("areaCode") String areaCode) {
        Map<String, Object> map = new HashMap<>();
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, size);
            if (token != null) {
                System.out.println(1);
                String id = jwtUtil.extractUsername(token.substring(6));
                List<TD> list = tdRepository.querySelectTDtem(title, areaCode, contentTypeId, id, pageRequest);
                int cnt = tdRepository.CountSelectTDtem(title, areaCode, contentTypeId, id);
                // System.out.println(cnt);
                map.put("cnt", (cnt - 1) / size + 1);
                map.put("list", list);
            } else {
                List<TD> list = tdRepository.querySelectTD(title, areaCode, contentTypeId, pageRequest);
                int cnt = tdRepository.CountSelectTD(title, areaCode, contentTypeId);
                // System.out.println(cnt);
                map.put("cnt", (cnt - 1) / size + 1);
                map.put("list", list);
            }
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // tourapi 상세페이지
    // Param : contentId(Code)
    // Return : contentId에 해당하는 데이터
    @GetMapping(value = "/selectone")
    public Map<String, Object> gettourapiselectone(@RequestParam("contentId") String contentId) {
        Map<String, Object> map = new HashMap<>();
        try {
            TD td = tdRepository.querySelectOneTD(contentId);
            map.put("TD", td);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;

    }

    // 여행지 선택시 거리별 조회
    @GetMapping(value = "/distance")
    public Map<String, Object> locationDistance(@RequestHeader(name = "TOKEN", required = false) String token,
            @RequestParam("areaCode") String areaCode, @RequestParam("xmap") Float xmap, @RequestParam("page") int page,
            @RequestParam("size") int size, @RequestParam("ymap") Float ymap,
            @RequestParam("contentTypeId") String contentTypeId,
            @RequestParam(name = "kilometer", defaultValue = "15") double kilometer) {
        Map<String, Object> map = new HashMap<>();
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, size);
            if (token != null) {
                String id = jwtUtil.extractUsername(token.substring(6));
                List<TD> list1 = tdRepository.querySelectdistanceTDtem(areaCode, contentTypeId, xmap, ymap, kilometer,
                        id, pageRequest);
                int cnt = tdRepository.CountSelectdistanceTDtem(areaCode, contentTypeId, xmap, ymap, kilometer, id);
                map.put("cnt", (cnt - 1) / size + 1);
                map.put("distanceList", list1);
            } else {
                List<TD> list1 = tdRepository.querySelectdistanceTD(areaCode, contentTypeId, xmap, ymap, kilometer,
                        pageRequest);
                int cnt = tdRepository.CountSelectdistanceTD(areaCode, contentTypeId, xmap, ymap, kilometer);
                map.put("cnt", (cnt - 1) / size + 1);
                map.put("distanceList", list1);
            }

            map.put("status", 200);

        } catch (

        Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;

    }

    // 임시장소 저장
    // Parameter : header(토큰), param(type), body(title, addr, xlocation, ylocation)
    // 성공 return : status(200)
    @PostMapping(value = "/TDtem/insert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> TDtemInsert(@RequestParam(name = "type") Integer type,
            @RequestParam(name = "city") Integer city, @RequestHeader("token") String token, @ModelAttribute TD td,
            @RequestParam(name = "file", required = false) MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        try {
            String id = jwtUtil.extractUsername(token.substring(6));
            Member member = mRepository.findById(id).orElseThrow();
            if (member != null && member.getToken().equals(token.substring(6))
                    && !jwtUtil.isTokenExpired(token.substring(6))) {
                TD td1 = new TD();
                td1.setTitle(td.getTitle());
                td1.setAddr(td.getAddr());
                td1.setTdtype(typeRepository.getById(type));
                td1.setXlocation(td.getXlocation());
                td1.setYlocation(td.getYlocation());
                td1.setCity(cityRepository.getById(city));
                td1.setState(0);
                td1.setUser(id);
                // System.out.println(td1);
                if (file != null) {
                    TDImg tdImg = new TDImg();
                    tdImg.setTd(tdRepository.save(td1));
                    tdImg.setImage(file.getBytes());
                    tdImg.setImagename(file.getOriginalFilename());
                    tdImg.setImagesize(file.getSize());
                    tdImg.setImagetype(file.getContentType());
                    tdimgRepository.save(tdImg);
                    td1.setFirstimage("//127.0.0.1:8080/REST/travel/select_image?no=" + tdImg.getTd().getNo());
                    tdRepository.save(td1);
                    map.put("no", tdImg.getTd().getNo());
                } else {
                    td1.setFirstimage("//127.0.0.1:8080/REST/travel/select_image?no=" + tdRepository.save(td1).getNo());
                    tdRepository.save(td1);
                    map.put("no", tdRepository.save(td1).getNo());
                }
                map.put("status", 200);
            } else {
                map.put("status", 578);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/REST/travel/select_image?no=이미지주소
    @GetMapping(value = "/select_image")
    public ResponseEntity<byte[]> selectImage(@RequestParam(name = "no") TD td) throws IOException {
        try {

            // System.out.println(td.getNo());
            TDImg tdImg = tdimgRepository.querySelectByTD(td);
            // System.out.println(tdImg.getNo());
            if (tdImg.getImage().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (tdImg.getImagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (tdImg.getImagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (tdImg.getImagetype().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }

                // 클래스명 response = new 클래스명( 생성자선택 )
                ResponseEntity<byte[]> response = new ResponseEntity<>(tdImg.getImage(), headers, HttpStatus.OK);
                // System.out.println(tdImg.getImage());
                return response;
            }
            return null;

        }
        // 오라클에 이미지를 읽을 수 없을 경우
        catch (Exception e) {
            InputStream is = resourceLoader.getResource(DEFAULTIMAGE).getInputStream();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
            return response;
        }
    }

    // 좋아요 기능
    // Header : token 필요
    // Body : 게시판 번호 필요
    // return : 같은아이디로 1번누르면 좋아요1증가 2번누르면 좋아요1감소
    @PostMapping(value = "/good", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> addgood(@RequestHeader("TOKEN") String token,
            @RequestParam("contentid") String contentid) {
        Map<String, Object> map = new HashMap<>();
        try {
            String id = jwtUtil.extractUsername(token.substring(6));
            Member member = mRepository.findById(id).orElseThrow();
            if (member != null && member.getToken().equals(token.substring(6))
                    && !jwtUtil.isTokenExpired(token.substring(6))) {
                Good good = new Good();
                TD td = tdRepository.querySelectOneTD(contentid);
                if (goodRepository.queryselectgoodTD(td, member) == null) {
                    good.setTd(td);
                    good.setMember(member);
                    goodRepository.save(good);
                    TD td1 = tdRepository.querySelectOneTDno(td.getNo());
                    td1.setGood(goodRepository.countByTd_no(td.getNo()));
                    tdRepository.save(td1);
                    map.put("goodresult", goodRepository.queryselectgoodstateTD(td, member).isPresent());

                } else {
                    Good good1 = goodRepository.queryselectgoodTD(td, member);
                    good1.setTd(null);
                    good1.setMember(null);
                    goodRepository.delete(good1);
                    TD td1 = tdRepository.querySelectOneTDno(td.getNo());
                    td1.setGood(goodRepository.countByTd_no(td.getNo()));
                    tdRepository.save(td1);
                    map.put("goodresult", goodRepository.queryselectgoodstateTD(td, member).isPresent());
                }
                int goodCnt = goodRepository.queryCountByTD(td);
                map.put("status", 200);
                map.put("good", goodCnt);
            } else {
                map.put("status", 578);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 좋아요 버튼 적용확인(꽉찬하트 true, 빈하트 false)
    @PostMapping(value = "/good/state", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> addgoodstate(@RequestHeader("TOKEN") String token,
            @RequestParam("contentid") String contentid) {
        Map<String, Object> map = new HashMap<>();
        try {
            String id = jwtUtil.extractUsername(token.substring(6));
            Member member = mRepository.findById(id).orElseThrow();
            if (member != null && member.getToken().equals(token.substring(6))
                    && !jwtUtil.isTokenExpired(token.substring(6))) {
                TD td = tdRepository.querySelectOneTD(contentid);
                map.put("goodresult", goodRepository.queryselectgoodstateTD(td, member).isPresent());
                map.put("status", 200);
            } else {
                map.put("status", 578);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 일정저장
    // total:[{save1:[{TD},{TD}],
    // save2:[{TD},{TD}]}
    // ]

    @PostMapping(value = "/TDsave", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> TDsave(@RequestHeader("TOKEN") String token, @RequestParam("title") String title,
            @RequestBody Map<String, Object> TDmap) {
        Map<String, Object> map = new HashMap<>();
        try {
            String id = jwtUtil.extractUsername(token.substring(6));
            Member member = mRepository.findById(id).orElseThrow();
            if (member != null && member.getToken().equals(token.substring(6))
                    && !jwtUtil.isTokenExpired(token.substring(6))) {
                TDSave tdsave = new TDSave();
                tdsave.setMember(member);
                tdsave.setTitle(title);
                ObjectMapper mapper = new ObjectMapper();
                tdsave.setTd(mapper.writeValueAsString(TDmap));
                tdsaveRepository.save(tdsave);
                map.put("status", 200);
            } else {
                map.put("status", 578);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 일정 공유
    @PostMapping(value = "/TDsave_board", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> TDsaveboard(@RequestHeader("TOKEN") String token, @RequestParam("title") String title,
            @RequestBody Map<String, Object> TDmap) {
        Map<String, Object> map = new HashMap<>();
        try {
            String id = jwtUtil.extractUsername(token.substring(6));
            Member member = mRepository.findById(id).orElseThrow();
            if (member != null && member.getToken().equals(token.substring(6))
                    && !jwtUtil.isTokenExpired(token.substring(6))) {
                TDSave tdsave = new TDSave();
                tdsave.setMember(member);
                tdsave.setTitle(title);
                tdsave.setState(2);
                ObjectMapper mapper = new ObjectMapper();
                tdsave.setTd(mapper.writeValueAsString(TDmap));
                tdsaveRepository.save(tdsave);
                map.put("status", 200);
            } else {
                map.put("status", 578);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
}
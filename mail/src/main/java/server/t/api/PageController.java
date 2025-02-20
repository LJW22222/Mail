package server.t.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import server.t.domain.SurveyService;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final SurveyService surveyService;

    @GetMapping("/createPage")
    public String createSurveyForm() {
        return "survey-form";
    }

    @PostMapping("/createPage")
    public String redirectSurveyForm() {
        return "redirect:/surveys";
    }

    @GetMapping("/list")
    public String getSurveyList(Model model) {
        model.addAttribute("surveyList", surveyService.getSurveyPageList());
        System.out.println(surveyService.getSurveyPageList());
        return "surveyPageList";
    }

//-----------------------아래 컨트롤러 메서드 수정 필요 -----------------//

//    //제휴업체 설문 페이지 관련 빈 DTO 객체 hmtl로 전달
//    @GetMapping("/company")
//    public String getCompanyPage(Model model) {
//        CompanyRequestDto companyRequestDto = new CompanyRequestDto();
//        model.addAttribute("companyRequestDto", companyRequestDto);
//        return "company";
//    }
//    //운영진 설문 페이지 관련 빈 DTO 객체 hmtl로 전달
//    @GetMapping("/management")
//    public String getManagementPage(Model model) {
//        ManagementFormDTO managementDTO = new ManagementFormDTO();
//        model.addAttribute("managementDTO", managementDTO);
//        return "management";
//    }

    //설문 조사 후 성공 페이지
    @GetMapping("/success")
    public String successPage(){
        return "success";
    }


    //설문 조사 후 성공 페이지
    @GetMapping("/test")
    public String testSubmit(){
        return "survey-form";
    }



//    @GetMapping("/view")
//    public String viewForm() {
//        return "form_view"; // Assuming you have a Thymeleaf or JSP view named "form_view"
//    }
//
//    @PostMapping("/form/save")
//    @ResponseBody
//    public String saveForm(@RequestBody RequestSurveyFormDataDTO data) {
//        formData = data;
//        System.out.println("Received formData: " + formData);
//        return "Form data saved successfully!";
//    }
//
//    @GetMapping("/load")
//    @ResponseBody
//    public RequestSurveyFormDataDTO loadForm() {
//        return formData;
//    }

}

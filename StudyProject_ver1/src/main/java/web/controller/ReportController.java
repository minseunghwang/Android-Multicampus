package web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {
	@RequestMapping(value = "/test.do", method = RequestMethod.POST)
	@ResponseBody
	public String test(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String fileName = file.getOriginalFilename();
		System.out.println("여기에나와야돼 : "+fileName);
		String path = request.getRealPath("/uploads/"); // 저장되는 path
		String fff = path + fileName;
		System.out.println(fff);

		if (!file.isEmpty()) {
			File f = new File(fff);
			try {
				file.transferTo(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		ImagePrediction ip = new ImagePrediction();
		String result = null;

		try {
			result = ip.ms(fff);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
		
		
		
		return result;
	}

	@RequestMapping(value = "/test.do", method = RequestMethod.GET)
	   public String test() {
	      return "reportForm";
	   }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/report.do", method = RequestMethod.GET)
	   public String form() {
	      return "reportForm";
	   }
	
	
	@RequestMapping(value = "/report.do", method = RequestMethod.POST)
	public ModelAndView upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		System.out.println(file);
		String fileName = file.getOriginalFilename();
		String path = request.getRealPath("/uploads/"); // 저장되는 path
		
		String fff = path + fileName;

		if (!file.isEmpty()) {
			File f = new File(fff);
			try {
				file.transferTo(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("imgname", fileName);
		ImagePrediction ip = new ImagePrediction();
		String result = null;
		try {
			result = ip.ms(fff);
			System.out.println("얌냠냠");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		mav.addObject("output",result);
		mav.setViewName("reportResult");
				
		
		return mav;
	}
}

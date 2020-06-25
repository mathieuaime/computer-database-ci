package com.excilys.computerdatabase.web.controller;

import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companies")
public class CompanyController {

  private final CompanyService companyService;

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @GetMapping
  public String list(Model model, Pageable pageable) {
    Page<CompanyDto> companies = companyService.find(pageable);

    //model.addAttribute("companies", companies);
    model.addAttribute("companies", companies.getContent());
    model.addAttribute("count", companies.getTotalElements());
    model.addAttribute("hasPrev", companies.hasPrevious());
    model.addAttribute("prev", companies.previousOrFirstPageable().getPageNumber());
    model.addAttribute("hasNext", companies.hasNext());
    model.addAttribute("next", companies.nextOrLastPageable().getPageNumber());

    return "company-list";
  }

  @GetMapping("/{id}")
  public String info(Model model, @PathVariable("id") long id) {
    companyService.findById(id).ifPresentOrElse(
        companyDto -> model.addAttribute("company", companyDto),
        () -> model.addAttribute("error", "Company not found"));

    return "company-details";
  }
}
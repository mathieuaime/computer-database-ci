package com.excilys.computerdatabase.ui.view.companies;

import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.dto.CompanyDto;
import com.excilys.computerdatabase.ui.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

@Route(value = "companies", layout = MainLayout.class)
@PageTitle("Companies | Computer Database")
public class CompanyView extends VerticalLayout {
  private final CompanyService companyService;

  final Grid<CompanyDto> grid;
  final TextField filter;

  public CompanyView(CompanyService companyService) {
    this.companyService = companyService;
    this.grid = new Grid<>(CompanyDto.class);
    this.filter = new TextField();

    HorizontalLayout actions = new HorizontalLayout(filter);
    add(actions, grid);

    setupGrid();

    setupFilter();

    listCompanies(null);
  }

  private void setupGrid() {
    grid.setColumns("id", "name");
    grid.setSortableColumns("id", "name");

    grid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);
  }

  private void setupFilter() {
    filter.setPlaceholder("Search by name");
    filter.setValueChangeMode(ValueChangeMode.EAGER);
    filter.addValueChangeListener(e -> {
      if (e.getValue().length() > 3) {
        listCompanies(e.getValue());
      }
    });
  }

  void listCompanies(String filterText) {
    if (StringUtils.isEmpty(filterText)) {
      grid.setItems(companyService.find(Pageable.unpaged()).getContent());
    } else {
      grid.setItems(companyService.search(filterText, Pageable.unpaged()).getContent());
    }
  }
}
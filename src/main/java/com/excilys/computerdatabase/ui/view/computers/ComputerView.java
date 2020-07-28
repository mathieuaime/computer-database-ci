package com.excilys.computerdatabase.ui.view.computers;

import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.dto.ComputerDto;
import com.excilys.computerdatabase.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Computers | Computer Database")
public class ComputerView extends VerticalLayout {
  private final ComputerService computerService;

  private final Grid<ComputerDto> grid;
  private final TextField filter;
  private final DateTimeFormatter formatter =
      DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
          .withZone(ZoneId.systemDefault());

  public ComputerView(ComputerService computerService, ComputerEditor editor) {
    this.computerService = computerService;
    this.grid = new Grid<>(ComputerDto.class);
    this.filter = new TextField();
    Button addNewBtn = new Button("Add Computer", VaadinIcon.PLUS.create());

    HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
    add(actions, grid, editor);

    setupGrid();

    setupFilter();

    grid.asSingleSelect().addValueChangeListener(e -> editor.editComputer(e.getValue()));

    addNewBtn.addClickListener(e -> {
      ComputerDto newComputer = ComputerDto.builder().introduced(Instant.now()).build();
      editor.editComputer(newComputer);
    });

    editor.setChangeHandler(() -> {
      editor.setVisible(false);
      listComputers(filter.getValue());
    });

    listComputers(null);
  }

  private void setupGrid() {
    grid.setColumns("id", "name");
    grid.setSortableColumns("id", "name");

    grid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);

    grid.addColumn(dto -> formatter.format(dto.getIntroduced()))
        .setHeader("Introduced");

    grid.addColumn(
        dto -> dto.getDiscontinued() != null ? formatter.format(dto.getDiscontinued()) : "")
        .setHeader("Discontinued");

    grid.addColumn(dto -> dto.getCompany() != null ? dto.getCompany().getName() : "")
        .setSortable(true)
        .setHeader("Company");
  }

  private void setupFilter() {
    filter.setPlaceholder("Search by name");
    filter.setValueChangeMode(ValueChangeMode.EAGER);
    filter.addValueChangeListener(e -> {
      if (e.getValue().length() > 3) {
        listComputers(e.getValue());
      }
    });
  }

  void listComputers(String filterText) {
    if (StringUtils.isEmpty(filterText)) {
      grid.setItems(computerService.find(Pageable.unpaged()).getContent());
    } else {
      grid.setItems(computerService.search(filterText, Pageable.unpaged()).getContent());
    }
  }
}
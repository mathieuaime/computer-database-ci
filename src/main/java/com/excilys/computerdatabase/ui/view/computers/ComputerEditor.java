package com.excilys.computerdatabase.ui.view.computers;

import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.dto.ComputerDto;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class ComputerEditor extends VerticalLayout implements KeyNotifier {
  private final ComputerService computerService;

  private ComputerDto computerDto;

  TextField name = new TextField("Name");

  Button save = new Button("Save", VaadinIcon.CHECK.create());
  Button cancel = new Button("Cancel");
  Button delete = new Button("Delete", VaadinIcon.TRASH.create());
  HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

  Binder<ComputerDto> binder = new Binder<>(ComputerDto.class);

  private ChangeHandler changeHandler;

  public ComputerEditor(ComputerService computerService) {
    this.computerService = computerService;

    add(name, actions);

    binder.bindInstanceFields(this);

    setSpacing(true);

    save.getElement().getThemeList().add("primary");
    delete.getElement().getThemeList().add("error");

    addKeyPressListener(Key.ENTER, e -> save());

    save.addClickListener(e -> save());
    delete.addClickListener(e -> delete());
    cancel.addClickListener(e -> setVisible(false));
    setVisible(false);
  }

  void delete() {
    computerService.delete(computerDto.getId());
    changeHandler.onChange();
  }

  void save() {
    computerDto = computerService.save(computerDto);
    changeHandler.onChange();
  }

  public interface ChangeHandler {
    void onChange();
  }

  public void editComputer(ComputerDto c) {
    if (c == null) {
      setVisible(false);
      return;
    }
    final boolean persisted = c.getId() != null;

    if (persisted) {
      computerDto = computerService.findById(c.getId()).get();
    } else {
      computerDto = c;
    }

    cancel.setVisible(persisted);
    binder.setBean(computerDto);
    setVisible(true);
    name.focus();
  }

  public void setChangeHandler(ChangeHandler changeHandler) {
    this.changeHandler = changeHandler;
  }
}

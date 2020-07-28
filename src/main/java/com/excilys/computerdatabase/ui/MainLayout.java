package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.ui.view.companies.CompanyView;
import com.excilys.computerdatabase.ui.view.computers.ComputerView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {
  public MainLayout() {
    createHeader();
    createDrawer();
  }

  private void createHeader() {
    H1 logo = new H1("Computer Database");
    logo.addClassName("logo");

    HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);

    header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    header.setWidth("100%");
    header.addClassName("header");

    addToNavbar(header);
  }

  private void createDrawer() {
    addLink(new RouterLink("Computers", ComputerView.class));
    addLink(new RouterLink("Companies", CompanyView.class));
  }

  private void addLink(RouterLink link) {
    link.setHighlightCondition(HighlightConditions.sameLocation());
    addToDrawer(new VerticalLayout(link));
  }
}

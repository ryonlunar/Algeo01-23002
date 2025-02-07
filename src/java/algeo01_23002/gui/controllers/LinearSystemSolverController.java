package algeo01_23002.gui.controllers;

import atlantafx.base.controls.Breadcrumbs;
import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LinearSystemSolverController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Breadcrumbs breadCrumbs;

    @FXML
    ToggleSwitch themeToggler;

    @SuppressWarnings("unchecked")
    public void initialize(){
        if(Application.getUserAgentStylesheet() == "/atlantafx/base/theme/cupertino-light.css"){
            themeToggler.setSelected(false);
        } else {
            themeToggler.setSelected(true);
        }
        String[] menuItems = {"Home", "Linear System Solver"};
        Breadcrumbs.BreadCrumbItem<String> rootItem = Breadcrumbs.buildTreeModel(
                menuItems
        );

        breadCrumbs.setSelectedCrumb(rootItem);

    }

    @FXML
    public void onThemeTogglerClicked() {
        if(themeToggler.isSelected()){
            Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        } else {
            Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
        }
    }

    @FXML
    public void onCrumbsAction(Breadcrumbs.BreadCrumbActionEvent event) throws IOException {
        String selectedCrumb = event.getSelectedCrumb().getValue().toString();

        if (selectedCrumb.equals("Home")) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/main-menu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }

    }

    public void onGaussianEliminationMethodClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/linear_system_solver/gaussian-elimination-method.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    public void onGaussJordanEliminationMethodClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/linear_system_solver/gauss-jordan-elimination-method.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    public void onCramersRuleMethodClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/linear_system_solver/cramers-rule-method.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    public void onInverseMethodClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/algeo01_23002/gui/menus/linear_system_solver/inverse-method.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}

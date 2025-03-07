package com.java.app.controllers;

import com.java.app.classes.PasswordSecure;
import com.java.app.components.*;
import com.java.app.entities.*;
import com.java.app.enums.*;
import com.java.app.exceptions.ParentNotFoundException;
import com.java.app.exceptions.StudentNotFoundException;
import com.java.app.exceptions.UserNotFoundException;
import com.java.app.objects.Avatars;
import com.java.app.objects.FileChoosers;
import com.java.app.services.*;
import com.java.app.stages.StageInitializer;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.java.app.Resources.string;
import static com.java.app.data.App.*;
import static com.java.app.objects.Alerts.*;
import static com.java.app.objects.Dates.setRussianDateFormat;
import static com.java.app.objects.Sounds.*;
import static com.java.app.stages.StageInitializer.setStage;

public class MainController extends ControllerBase implements Initializable {
    private String avatarUrl;

    @Qualifier("StudentsService")
    private final StudentsService studentsService =
            getApplicationContext().getBean(StudentsService.class);

    @Qualifier("ParentsService")
    private final ParentsService parentsService =
            getApplicationContext().getBean(ParentsService.class);

    @Qualifier("ParentsService")
    private final PerformancesService performancesService =
            getApplicationContext().getBean(PerformancesService.class);

    @Qualifier("UsersService")
    private final UsersService usersService =
            getApplicationContext().getBean(UsersService.class);

    // Menu
    @FXML public Label userDisplayName;
    @FXML public ImageView userAvatar;
    @FXML public JFXButton studentsBtn;
    @FXML public JFXButton parentsBtn;
    @FXML public JFXButton performancesBtn;
    @FXML public JFXButton settingsBtn;
    @FXML public JFXButton logoutBtn;

    // Panes
    @FXML public AnchorPane studentsPane;
    @FXML public AnchorPane parentsPane;
    @FXML public AnchorPane performancesPane;
    @FXML public AnchorPane settingsPane;

    // Students page
    @FXML public TextField searchStudentField;
    @FXML public TableView<Student> studentsTable;
    @FXML public Button findStudentBtn;
    @FXML public Button refreshStudentsBtn;
    @FXML public Button deleteStudentBtn;
    @FXML public Button addStudentBtn;
    @FXML public Button editStudentBtn;
    @FXML public TableColumn<Student, String> studentCardId;
    @FXML public TableColumn<Student, String> studentFullName;
    @FXML public TableColumn<Student, String> studentGender;
    @FXML public TableColumn<Student, LocalDate> studentDayOfBirth;
    @FXML public TableColumn<Student, String> studentAddress;
    @FXML public TableColumn<Student, String> studentPhone;

    @FXML public TextField addStudentCardIdField;
    @FXML public TextField addStudentPhoneField;
    @FXML public TextArea addStudentAddressField;
    @FXML public ComboBox<String> addStudentGenderComboBox;
    @FXML public TextArea addStudentFullNameField;
    @FXML public DatePicker addStudentDateOfBirthField;
    @FXML public Button addStudentConfirmBtn;
    @FXML public Button cancelAddStudentBtn;
    @FXML public Button hideAddStudentPaneBtn;
    @FXML public AnchorPane addStudentPane;

    @FXML public AnchorPane editStudentPane;
    @FXML public TextField editStudentCardIdField;
    @FXML public TextArea editStudentFullNameField;
    @FXML public ComboBox<String> editStudentGenderComboBox;
    @FXML public DatePicker editStudentDateOfBirthField;
    @FXML public TextArea editStudentAddressField;
    @FXML public TextField editStudentPhoneField;
    @FXML public Button editStudentConfirmBtn;
    @FXML public Button cancelEditStudentBtn;
    @FXML public Button hideEditStudentPaneBtn;

    // Parents page
    @FXML public TextField searchParentField;
    @FXML public TableView<Parent> parentsTable;
    @FXML public Button findParentBtn;
    @FXML public Button refreshParentsBtn;
    @FXML public Button deleteParentBtn;
    @FXML public Button addParentBtn;
    @FXML public Button editParentBtn;
    @FXML public TableColumn<Parent, String> parentFullName;
    @FXML public TableColumn<Parent, String> parentWorkPhone;
    @FXML public TableColumn<Parent, String> parentAddress;
    @FXML public TableColumn<Parent, Student> parentStudent;

    @FXML public AnchorPane addParentPane;
    @FXML public TextField addParentPhoneField;
    @FXML public TextArea addParentFullNameField;
    @FXML public ComboBox<Student> addParentStudentComboBox;
    @FXML public TextArea addParentAddressField;
    @FXML public Button addParentConfirmBtn;
    @FXML public Button cancelAddParentBtn;
    @FXML public Button hideAddParentPaneBtn;

    @FXML public AnchorPane editParentPane;
    @FXML public TextField editParentPhoneField;
    @FXML public TextArea editParentFullNameField;
    @FXML public ComboBox<Student> editParentStudentComboBox;
    @FXML public TextArea editParentAddressField;
    @FXML public Button editParentConfirmBtn;
    @FXML public Button cancelEditParentBtn;
    @FXML public Button hideEditParentPaneBtn;

    // Performances page
    @FXML public TextField searchPerformanceField;
    @FXML public TableView<Performance> performancesTable;
    @FXML public Button findPerformanceBtn;
    @FXML public Button refreshPerformancesBtn;
    @FXML public Button deletePerformanceBtn;
    @FXML public Button addPerformanceBtn;
    @FXML public Button editPerformanceBtn;
    @FXML public TableColumn<Performance, String> performanceSubject;
    @FXML public TableColumn<Performance, LocalDate> performanceDate;
    @FXML public TableColumn<Performance, Byte> performanceGrade;
    @FXML public TableColumn<Performance, Student> performanceStudent;

    @FXML public AnchorPane addPerformancePane;
    @FXML public TextArea addPerformanceSubjectField;
    @FXML public TextField addPerformanceGradeField;
    @FXML public DatePicker addPerformanceDateField;
    @FXML public ComboBox<Student> addPerformanceStudentComboBox;
    @FXML public Button addPerformanceConfirmBtn;
    @FXML public Button cancelAddPerformanceBtn;
    @FXML public Button hideAddPerformancePaneBtn;

    @FXML public AnchorPane editPerformancePane;
    @FXML public TextArea editPerformanceSubjectField;
    @FXML public TextField editPerformanceGradeField;
    @FXML public DatePicker editPerformanceDateField;
    @FXML public ComboBox<Student> editPerformanceStudentComboBox;
    @FXML public Button editPerformanceConfirmBtn;
    @FXML public Button cancelEditPerformanceBtn;
    @FXML public Button hideEditPerformancePaneBtn;

    // Settings Page
    @FXML public VBox settingsList;
    @FXML public AnchorPane settingsChangeNamePane;
    @FXML public AnchorPane settingsChangePasswordPane;
    @FXML public AnchorPane settingsChangeAvatarPane;
    @FXML public AnchorPane settingsChangeDateOfBirthPane;

    @FXML public TextField settingsNewNameField;
    @FXML public JFXButton settingsSaveNewNameBtn;
    @FXML public JFXButton settingsHideChangeNamePaneBtn;

    @FXML public PasswordField settingsNewPasswordField;
    @FXML public JFXButton settingsSaveNewPasswordBtn;
    @FXML public JFXButton settingsHideChangePasswordPaneBtn;

    @FXML public DatePicker settingsNewDateOfBirthField;
    @FXML public Button hideChangeDateOfBirthPaneBtn;

    @FXML public TextField settingsNewAvatarField;
    @FXML public JFXButton settingsSaveNewAvatarBtn;
    @FXML public JFXButton settingsHideChangeAvatarBtn;
    @FXML public Button openAvatarBtn;
    @FXML public JFXButton deleteBtn;

    public Node[] nodes;
    public MenuItemController[] settingsControllers;

    // DataLists
    @FXML public ObservableList<String> genders;
    @FXML public ObservableList<Parent> parents;
    @FXML public ObservableList<Student> students;
    @FXML public ObservableList<Performance> performances;
    @FXML public ObservableList<Setting> settings = FXCollections.observableArrayList(
            new Setting(
                    "USER",
                    "Имя пользователя",
                    getUser().getDisplayName()
            ), new Setting(
                    "LOCK",
                    "Пароль",
                    "********"
            ), new Setting(
                    "IMAGE",
                    "Аватар",
                    "skuf"
            ), new Setting(
                    "CALENDAR",
                    "Дата рождения",
                    getUser().getDateOfBirth()
            )
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int size = settings.size();
        studentsPane.setVisible(true);
        nodes = new Node[size];
        settingsControllers = new MenuItemController[size];

        try {
            for (int i = 0; i < nodes.length; i++) {
                FXMLLoader loader = new FXMLLoader(
                        Objects.requireNonNull(
                                getClass().getResource("/views/settings-menu-item.fxml")
                        )
                );
                nodes[i] = loader.load();
                Setting setting = settings.get(i);
                MenuItemController controller = getMenuItemController(loader, i);
                settingsControllers[i] = controller;
                controller.setInfo(setting.getName(), setting.getCurrentValue(), setting.getIcon());
                settingsList.getChildren().add(nodes[i]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Buttons
        setupTooltips(findStudentBtn, editStudentBtn, refreshStudentsBtn, deleteStudentBtn, addStudentBtn);
        setupTooltips(findParentBtn, editParentBtn, refreshParentsBtn, deleteParentBtn, addParentBtn);

        // Students columns
        studentCardId.setCellValueFactory(new PropertyValueFactory<>("cardId"));
        studentFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        studentGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        studentDayOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        studentDayOfBirth.setCellFactory(setRussianDateFormat());
        studentAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        studentPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Parents columns
        parentFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        parentAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        parentWorkPhone.setCellValueFactory(new PropertyValueFactory<>("workPhone"));
        parentStudent.setCellValueFactory(new PropertyValueFactory<>("student"));

        // Performances columns
        performanceGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        performanceDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        performanceDate.setCellFactory(setRussianDateFormat());
        performanceStudent.setCellValueFactory(new PropertyValueFactory<>("student"));
        performanceSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));

        // Lists and tables init
        genders = FXCollections.observableArrayList(string("man"), string("woman"));
        students = FXCollections.observableArrayList(studentsService.getStudents());
        parents = FXCollections.observableArrayList(parentsService.getParents());
        performances = FXCollections.observableArrayList(performancesService.getPerformances());

        studentsTable.setItems(students);
        parentsTable.setItems(parents);
        performancesTable.setItems(performances);

        // Comboboxes init
        addStudentGenderComboBox.setItems(genders);
        editStudentGenderComboBox.setItems(genders);

        addParentStudentComboBox.setItems(students);
        editParentStudentComboBox.setItems(students);

        addPerformanceStudentComboBox.setItems(students);
        editPerformanceStudentComboBox.setItems(students);

        // User data
        userDisplayName.setText(getUser().getDisplayName());
        try {
            Image img = new Image(Objects.requireNonNull(
                    StageInitializer.class.getResourceAsStream(
                            "/drawables/" + getUser().getAvatarUrl()
                    )
            ));
            userAvatar.setImage(img);
            userAvatar.setClip(new Circle(80, 80, 80));
        } catch (Exception ignored) {

        }
    }

    private @NotNull MenuItemController getMenuItemController(FXMLLoader loader, int index) {
        MenuItemController controller = loader.getController();
        controller.setListener((event) -> {
            switch (index) {
                case 0:
                    toggleNameAction();
                    break;
                case 1:
                    togglePasswordAction();
                    break;
                case 2:
                    toggleAvatarAction();
                    break;
                case 3:
                    toggleDateOfBirthAction();
                    break;
            }
        });
        return controller;
    }

    public void setupTooltips(Button findBtn, Button editBtn, Button refreshBtn, Button deleteBtn, Button addBtn) {
        Tooltip.install(findBtn, new MyTooltip("Поиск"));
        Tooltip.install(editBtn, new MyTooltip("Изменить"));
        Tooltip.install(refreshBtn, new MyTooltip("Обновить"));
        Tooltip.install(deleteBtn, new MyTooltip("Удалить"));
        Tooltip.install(addBtn, new MyTooltip("Добавить"));
    }

    @FXML
    public void onLogoutAction(ActionEvent event) {
        if (showLogoutDialog() == ButtonType.YES) {
            setUser(null);
            playSound(SoundTracks.BYE);
            setStage(event, Views.SIGNON);
        } else
            playSound(SoundTracks.CLICK, .4);
    }

    // Navigation
    @FXML
    public void onStudentsAction() {
        playSound(SoundTracks.CLICK, .4);
        studentsPane.setVisible(true);
        parentsPane.setVisible(false);
        performancesPane.setVisible(false);
        settingsPane.setVisible(false);
    }

    @FXML
    public void onParentsAction() {
        playSound(SoundTracks.CLICK, .4);
        studentsPane.setVisible(false);
        parentsPane.setVisible(true);
        performancesPane.setVisible(false);
        settingsPane.setVisible(false);
    }

    @FXML
    public void onPerformancesAction() {
        playSound(SoundTracks.CLICK, .4);
        studentsPane.setVisible(false);
        parentsPane.setVisible(false);
        performancesPane.setVisible(true);
        settingsPane.setVisible(false);
    }

    @FXML
    public void onSettingsAction() {
        playSound(SoundTracks.CLICK, .4);
        studentsPane.setVisible(false);
        parentsPane.setVisible(false);
        performancesPane.setVisible(false);
        settingsPane.setVisible(true);
    }

    // Students in menu
    @FXML
    public void onSearchStudentClicked() {
        playSound(SoundTracks.CLICK, .4);
        students = FXCollections.observableArrayList(
                studentsService.getStudentsByFullNameLike(
                        searchStudentField.getText()
                )
        );
        studentsTable.setItems(students);
    }

    @FXML
    public void onAddUserClicked() {
        playSound(SoundTracks.CLICK, .4);
        toggleAddStudentPane();
    }

    @FXML
    public void onDeleteUserClicked() {
        Student selected = studentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            playSound(SoundTracks.ERROR, .6);
            showError("Студент не был выбран");
        } else {
            if (
                    showConfirmation(
                            String.format(
                                    "Вы уверены, что хотите удалить студента %s?", selected
                            ), "Удаление"
                    ) == ButtonType.YES
            ) {
                List<Parent> parents = parentsService.getParentsByStudent(selected);
                List<Performance> performances = performancesService.getPerformancesByStudent(selected);

                if (!parents.isEmpty()) {
                    if (showConfirmation("Удалить родителей студента?", "Подтверждение") != ButtonType.YES)
                        return;
                    else {
                        if (!performances.isEmpty())
                            if (showConfirmation("Удалить успеваемость студента?", "Подтверждение") != ButtonType.YES)
                                return;
                            else {
                                parentsService.deleteParents(parents);
                                parentsTable.setItems(
                                        FXCollections.observableArrayList(
                                                parentsService.getParents()
                                        )
                                );

                                performancesService.deletePerformances(performances);
                                performancesTable.setItems(
                                        FXCollections.observableArrayList(
                                                performancesService.getPerformances()
                                        )
                                );
                            }
                    }
                }

                studentsService.deleteStudent(selected);
                playSound(SoundTracks.BELL);
                studentsTable.setItems(
                        FXCollections.observableArrayList(
                                studentsService.getStudents()
                        )
                );

                students = FXCollections.observableArrayList(studentsService.getStudents());
                addParentStudentComboBox.setItems(students);
                editParentStudentComboBox.setItems(students);
                addPerformanceStudentComboBox.setItems(students);
                editPerformanceStudentComboBox.setItems(students);
            }
        }
    }

    @FXML
    public void onRefreshStudentsClicked() {
        playSound(SoundTracks.LOADED, .6);
        studentsTable.setItems(
                FXCollections.observableArrayList(
                        studentsService.getStudents()
                )
        );
    }

    @FXML
    public void addStudentAction() {
        StringBuilder errors = new StringBuilder();

        String cardId = addStudentCardIdField.getText();
        String fullName = addStudentFullNameField.getText();
        String gender = addStudentGenderComboBox.getValue();
        LocalDate dateOfBirth = addStudentDateOfBirthField.getValue();
        String address = addStudentAddressField.getText();
        String phone = addStudentPhoneField.getText();

        if (cardId.isEmpty())
            errors.append("""
                    Номер книжки не указан
                    """);
        else
            try {
                studentsService.getStudentByCardId(cardId);
                errors.append("""
                        Этот номер книжки уже используется
                        """);
            } catch (StudentNotFoundException ignored) {
            }

        if (fullName.isEmpty())
            errors.append("""
                    Фио не указаны
                    """);

        if (gender == null)
            errors.append("""
                    Пол не указан
                    """);

        if (dateOfBirth == null)
            errors.append("""
                    Дата рождения не указана
                    """);

        if (address.isEmpty())
            errors.append("""
                    Адрес не указан
                    """);

        if (phone.isEmpty())
            errors.append("""
                    Телефон не указан
                    """);
        else
            try {
                studentsService.getStudentByPhone(phone);
                errors.append("""
                            Этот телефон уже используется
                            """);
            } catch (StudentNotFoundException ignored) {
            }

        if (!errors.isEmpty()) {
            playSound(SoundTracks.ACCESS_DENIED, .6);
            showError(errors.toString());
            return;
        }
        playSound(SoundTracks.CLICK, .4);

        Student student = new Student();
        student.setAddress(address);
        student.setGender(Objects.requireNonNull(gender).substring(0, 1));
        student.setPhone(phone);
        student.setCardId(cardId);
        student.setFullName(fullName);
        student.setDateOfBirth(dateOfBirth);
        studentsService.createStudent(student);
        hideAddStudentPane();

        List<Student> students = studentsService.getStudents();
        studentsTable.setItems(FXCollections.observableArrayList(students));

        playSound(SoundTracks.BELL, .4);
        showInformation(
                String.format("Студент %s успешно добавлен", cardId),
                string("success")
        );

        addStudentCardIdField.clear();
        addStudentFullNameField.clear();
        addStudentGenderComboBox.setValue(null);
        addStudentDateOfBirthField.setValue(null);
        addStudentAddressField.clear();
        addStudentPhoneField.clear();

        this.students = FXCollections.observableArrayList(studentsService.getStudents());
        addParentStudentComboBox.setItems(this.students);
        editParentStudentComboBox.setItems(this.students);
        addPerformanceStudentComboBox.setItems(this.students);
        editPerformanceStudentComboBox.setItems(this.students);
    }

    @FXML
    public void onEditStudentClicked() {
        Student selected = studentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            playSound(SoundTracks.ERROR, .6);
            showError("Студент не выбран");
            return;
        }
        playSound(SoundTracks.CLICK, .4);

        editStudentCardIdField.setEditable(false);
        editStudentCardIdField.setText(selected.getCardId());
        editStudentAddressField.setText(selected.getAddress());
        editStudentFullNameField.setText(selected.getFullName());
        editStudentPhoneField.setText(selected.getPhone());
        editStudentDateOfBirthField.setValue(selected.getDateOfBirth());
        editStudentGenderComboBox.setValue(selected.getGender().equals("М") ? string("man") : string("woman"));
        toggleEditStudentPane();
    }

    @FXML
    public void hideAddStudentPaneAction() {
        playSound(SoundTracks.CLICK, .4);
        hideAddStudentPane();
    }

    @FXML
    public void editStudentAction() {
        Student selected = studentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            playSound(SoundTracks.ERROR, .6);
            showError("Не выбран студент");
            return;
        }

        StringBuilder errors = new StringBuilder();

        String cardId = editStudentCardIdField.getText();
        String fullName = editStudentFullNameField.getText();
        String gender = editStudentGenderComboBox.getValue();
        LocalDate dateOfBirth = editStudentDateOfBirthField.getValue();
        String address = editStudentAddressField.getText();
        String phone = editStudentPhoneField.getText();

        if (fullName.isEmpty())
            errors.append("""
                    Фио не указаны
                    """);

        if (gender == null)
            errors.append("""
                    Пол не указан
                    """);

        if (dateOfBirth == null)
            errors.append("""
                    Дата рождения не указана
                    """);

        if (address.isEmpty())
            errors.append("""
                    Адрес не указан
                    """);

        if (phone.isEmpty())
            errors.append("""
                    Телефон не указан
                    """);

        if (!errors.isEmpty()) {
            playSound(SoundTracks.ACCESS_DENIED, .6);
            showError(errors.toString());
            return;
        }
        playSound(SoundTracks.CLICK, .4);

        Student student = new Student();
        student.setCardId(cardId);
        student.setAddress(address);
        student.setGender(Objects.requireNonNull(gender).substring(0, 1));
        student.setPhone(phone);
        student.setFullName(fullName);
        student.setDateOfBirth(dateOfBirth);
        studentsService.updateStudent(student);
        hideEditStudentPane();

        List<Student> students = studentsService.getStudents();
        studentsTable.setItems(FXCollections.observableArrayList(students));

        playSound(SoundTracks.BELL, .4);
        showInformation(
                String.format("Студент %s успешно изменен", selected.getCardId()),
                string("success")
        );

        addStudentCardIdField.clear();
        addStudentFullNameField.clear();
        addStudentGenderComboBox.setValue(null);
        addStudentDateOfBirthField.setValue(null);
        addStudentAddressField.clear();
        addStudentPhoneField.clear();

        this.students = FXCollections.observableArrayList(studentsService.getStudents());
        addParentStudentComboBox.setItems(this.students);
        editParentStudentComboBox.setItems(this.students);
        addPerformanceStudentComboBox.setItems(this.students);
        editPerformanceStudentComboBox.setItems(this.students);
    }

    @FXML
    public void hideEditStudentPaneAction() {
        playSound(SoundTracks.CLICK, .4);
        hideEditStudentPane();
    }

    // Student SubPanels Visibility
    private void hideAddStudentPane() {
        addStudentPane.setVisible(false);
    }

    private void toggleAddStudentPane() {
        addStudentPane.setVisible(!addStudentPane.isVisible());
    }

    private void hideEditStudentPane() {
        editStudentPane.setVisible(false);
    }

    private void toggleEditStudentPane() {
        editStudentPane.setVisible(!editStudentPane.isVisible());
    }

    // Parents
    @FXML
    public void onSearchParentClicked() {
        playSound(SoundTracks.CLICK, .4);
        parentsTable.setItems(
                FXCollections.observableArrayList(
                        parentsService.getParentsByFullNameLike(
                                "%" + searchParentField.getText() + "%"
                        )
                )
        );
    }

    @FXML
    public void onAddParentClicked() {
        playSound(SoundTracks.CLICK, .4);
        toggleAddParentPane();
    }

    @FXML
    public void onDeleteParentClicked() {
        Parent selected = parentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            playSound(SoundTracks.ERROR, .4);
            showError("Родитель не выбран");
        } else {
            if (
                    showConfirmation(
                            String.format(
                                    "Вы уверены, что хотите удалить родителя %s?", selected
                            ), "Удаление"
                    ) == ButtonType.YES
            ) {
                parentsService.deleteParent(selected);
                playSound(SoundTracks.BELL);
                parentsTable.setItems(
                        FXCollections.observableArrayList(
                                parentsService.getParents()
                        )
                );
            }
        }
    }

    @FXML
    public void onEditParentClicked() {
        Parent selected = parentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            playSound(SoundTracks.ERROR, .4);
            showError("Родитель не выбран");
            return;
        }
        playSound(SoundTracks.CLICK, .4);

        editParentAddressField.setText(selected.getAddress());
        editParentFullNameField.setText(selected.getFullName());
        editParentPhoneField.setText(selected.getWorkPhone());
        editParentStudentComboBox.setValue(selected.getStudent());
        toggleEditParentPane();
    }

    @FXML
    public void onRefreshParentsClicked() {
        playSound(SoundTracks.LOADED, .4);
        parentsTable.setItems(
                FXCollections.observableArrayList(
                        parentsService.getParents()
                )
        );
    }

    @FXML
    public void addParentAction() {
        StringBuilder errors = new StringBuilder();

        String fullName = addParentFullNameField.getText();
        Student student = addParentStudentComboBox.getValue();
        String address = addParentAddressField.getText();
        String phone = addParentPhoneField.getText();

        if (fullName.isEmpty())
            errors.append("""
                    Фио не указаны
                    """);
        else
            try {
                parentsService.getParentByFullName(fullName);
                errors.append("""
                        Эти фио уже используются
                        """);
            } catch (ParentNotFoundException ignored) {
            }

        if (student == null)
            errors.append("""
                    Студент не указан
                    """);

        if (address.isEmpty())
            errors.append("""
                    Адрес не указан
                    """);

        if (phone.isEmpty())
            errors.append("""
                    Телефон не указан
                    """);
        else
            try {
                parentsService.getParentByPhone(phone);
                errors.append("""
                        Этот телефон уже занят
                        """);
            } catch (ParentNotFoundException ignored) {
            }

        if (!errors.isEmpty()) {
            playSound(SoundTracks.ACCESS_DENIED, .4);
            showError(errors.toString());
            return;
        }
        playSound(SoundTracks.CLICK, .4);

        Parent parent = new Parent();
        parent.setAddress(address);
        parent.setStudent(student);
        parent.setWorkPhone(phone);
        parent.setFullName(fullName);
        parentsService.createParent(parent);
        hideAddParentPane();

        List<Parent> parents = parentsService.getParents();
        parentsTable.setItems(FXCollections.observableArrayList(parents));

        playSound(SoundTracks.BELL, .4);
        showInformation(
                String.format("Родитель %s успешно добавлен", parent.getFullName()),
                string("success")
        );

        addParentFullNameField.clear();
        addParentAddressField.clear();
        addParentPhoneField.clear();
        addParentStudentComboBox.setValue(null);
    }

    @FXML
    public void hideAddParentPaneAction() {
        playSound(SoundTracks.CLICK, .4);
        hideAddParentPane();
    }

    @FXML
    public void editParentAction() {
        Parent selected = parentsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            playSound(SoundTracks.ERROR, .4);
            showError("Родитель не выбран");
            return;
        }

        StringBuilder errors = new StringBuilder();

        Long id = selected.getId();
        String fullName = editParentFullNameField.getText();
        Student student = editParentStudentComboBox.getValue();
        String address = editParentAddressField.getText();
        String phone = editParentPhoneField.getText();

        if (fullName.isEmpty())
            errors.append("""
                    Фио не указаны
                    """);

        if (student == null)
            errors.append("""
                    Студент не указан
                    """);

        if (address.isEmpty())
            errors.append("""
                    Адрес не указан
                    """);

        if (phone.isEmpty())
            errors.append("""
                    Телефон не указан
                    """);
        else if (!phone.equals(selected.getWorkPhone()))
            try {
                parentsService.getParentByPhone(phone);
                errors.append("""
                            Этот телефон уже занят
                            """);
            } catch (ParentNotFoundException ignored) {
            }

        if (!errors.isEmpty()) {
            playSound(SoundTracks.ACCESS_DENIED, .4);
            showError(errors.toString());
            return;
        }
        playSound(SoundTracks.CLICK, .4);

        Parent parent = new Parent();
        parent.setId(id);
        parent.setAddress(address);
        parent.setStudent(student);
        parent.setWorkPhone(phone);
        parent.setFullName(fullName);
        parentsService.updateParent(parent);
        hideEditParentPane();

        List<Parent> parents = parentsService.getParents();
        parentsTable.setItems(FXCollections.observableArrayList(parents));

        playSound(SoundTracks.BELL, .4);
        showInformation(
                String.format("Родитель %s успешно изменен", selected.getFullName()),
                string("success")
        );

        editParentFullNameField.clear();
        editParentAddressField.clear();
        editParentPhoneField.clear();
        editParentStudentComboBox.setValue(null);
    }

    @FXML
    public void hideEditParentPaneAction() {
        playSound(SoundTracks.CLICK, .4);
        hideEditParentPane();
    }

    // Parent SubPanels Visibility
    private void hideAddParentPane() {
        addParentPane.setVisible(false);
    }

    private void toggleAddParentPane() {
        addParentPane.setVisible(!addParentPane.isVisible());
    }

    private void hideEditParentPane() {
        editParentPane.setVisible(false);
    }

    private void toggleEditParentPane() {
        editParentPane.setVisible(!editParentPane.isVisible());
    }

    // Performances
    @FXML
    public void onSearchPerformanceClicked() {
        playSound(SoundTracks.CLICK, .4);
        performancesTable.setItems(
                FXCollections.observableArrayList(
                        performancesService.getPerformancesBySubjectLike(
                                "%" + searchPerformanceField.getText() + "%"
                        )
                )
        );
    }

    @FXML
    public void onAddPerformanceClicked() {
        playSound(SoundTracks.CLICK, .4);
        toggleAddPerformancePane();
    }

    @FXML
    public void onDeletePerformanceClicked() {
        Performance selected = performancesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            playSound(SoundTracks.ERROR, .4);
            showError("Успеваемость не выбрана");
        } else {
            if (
                    showConfirmation(
                            String.format(
                                    "Вы уверены, что хотите удалить успеваемость %s?", selected
                            ), "Удаление"
                    ) == ButtonType.YES
            ) {
                performancesService.deletePerformance(selected);
                playSound(SoundTracks.BELL, .4);
                performancesTable.setItems(
                        FXCollections.observableArrayList(
                                performancesService.getPerformances()
                        )
                );
            }
        }
    }

    @FXML
    public void onEditPerformanceClicked() {
        Performance selected = performancesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            playSound(SoundTracks.ERROR);
            showError("Успеваемость не выбрана");
            return;
        }
        playSound(SoundTracks.CLICK, .4);

        editPerformanceGradeField.setText(String.valueOf(selected.getGrade()));
        editPerformanceSubjectField.setText(selected.getSubject());
        editPerformanceStudentComboBox.setValue(selected.getStudent());
        editPerformanceDateField.setValue(selected.getDate());
        toggleEditPerformancePane();
    }

    @FXML
    public void onRefreshPerformancesClicked() {
        playSound(SoundTracks.LOADED, .6);
        performancesTable.setItems(
                FXCollections.observableArrayList(
                        performancesService.getPerformances()
                )
        );
    }

    @FXML
    public void addPerformanceAction() {
        StringBuilder errors = new StringBuilder();

        String grade = addPerformanceGradeField.getText();
        Student student = addPerformanceStudentComboBox.getValue();
        String subject = addPerformanceSubjectField.getText();
        LocalDate date = addPerformanceDateField.getValue();
        byte numGrade = 0;

        if (grade.isEmpty())
            errors.append("""
                    Оценка не указана
                    """);
        else
            try {
                numGrade = Byte.parseByte(grade);
                if (numGrade < 2 || numGrade > 5)
                    errors.append("Оценка не находится в интервале (1 < Оценка < 6)");
            } catch (NumberFormatException ignored) {
                errors.append("Оценка не является числом");
            }


        if (student == null)
            errors.append("""
                    Студент не указан
                    """);

        if (date == null)
            errors.append("""
                    Дата не указана
                    """);

        if (subject.isEmpty())
            errors.append("""
                    Предмет не указан
                    """);

        if (!errors.isEmpty()) {
            playSound(SoundTracks.ACCESS_DENIED, .4);
            showError(errors.toString());
            return;
        }
        playSound(SoundTracks.CLICK, .4);

        Performance performance = new Performance();
        performance.setDate(date);
        performance.setGrade(numGrade);
        performance.setStudent(student);
        performance.setSubject(subject);
        performancesService.createPerformance(performance);
        hideAddPerformancePane();

        List<Performance> performances = performancesService.getPerformances();
        performancesTable.setItems(FXCollections.observableArrayList(performances));

        playSound(SoundTracks.BELL, .4);
        showInformation(
                String.format("Успеваемость %s успешно добавлена", performance),
                string("success")
        );

        addPerformanceGradeField.clear();
        addPerformanceSubjectField.clear();
        addPerformanceDateField.setValue(null);
        addPerformanceStudentComboBox.setValue(null);
    }

    @FXML
    public void hideAddPerformancePaneAction() {
        playSound(SoundTracks.CLICK, .4);
        hideAddPerformancePane();
    }

    @FXML
    public void editPerformanceAction() {
        Performance selected = performancesTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            playSound(SoundTracks.ERROR, .4);
            showError("Успеваемость не выбрана");
            return;
        }

        StringBuilder errors = new StringBuilder();

        Long id = selected.getId();
        String grade = editPerformanceGradeField.getText();
        LocalDate date = editPerformanceDateField.getValue();
        Student student = editPerformanceStudentComboBox.getValue();
        String subject = editPerformanceSubjectField.getText();
        byte numGrade = 0;

        if (grade.isEmpty())
            errors.append("""
                    Оценка не указана
                    """);
        else
            try {
                numGrade = Byte.parseByte(grade);
                if (numGrade < 2 || numGrade > 5)
                    errors.append("Оценка не находится в интервале (1 < Оценка < 6)");
            } catch (NumberFormatException ignored) {
                errors.append("Оценка не является числом");
            }


        if (student == null)
            errors.append("""
                    Студент не указан
                    """);

        if (date == null)
            errors.append("""
                    Дата не указана
                    """);

        if (subject.isEmpty())
            errors.append("""
                    Предмет не указан
                    """);

        if (!errors.isEmpty()) {
            playSound(SoundTracks.ACCESS_DENIED, .4);
            showError(errors.toString());
            return;
        }
        playSound(SoundTracks.CLICK, .4);

        Performance performance = new Performance();
        performance.setId(id);
        performance.setDate(date);
        performance.setGrade(numGrade);
        performance.setStudent(student);
        performance.setSubject(subject);
        performancesService.updatePerformance(performance);
        hideEditPerformancePane();

        List<Performance> performances = performancesService.getPerformances();
        performancesTable.setItems(FXCollections.observableArrayList(performances));

        playSound(SoundTracks.BELL, .4);
        showInformation(
                "Успеваемость успешно изменена",
                string("success")
        );

        editPerformanceGradeField.clear();
        editPerformanceSubjectField.clear();
        addPerformanceDateField.setValue(null);
        editPerformanceStudentComboBox.setValue(null);
    }

    @FXML
    public void hideEditPerformancePaneAction() {
        playSound(SoundTracks.CLICK, .4);
        hideEditPerformancePane();
    }

    // Performance SubPanels Visibility
    private void hideAddPerformancePane() {
        addPerformancePane.setVisible(false);
    }

    private void toggleAddPerformancePane() {
        addPerformancePane.setVisible(!addPerformancePane.isVisible());
    }

    private void hideEditPerformancePane() {
        editPerformancePane.setVisible(false);
    }

    private void toggleEditPerformancePane() {
        editPerformancePane.setVisible(!editPerformancePane.isVisible());
    }

    // Settings
    @FXML
    private void changeNameAction() {
        String error = "";
        String newName = settingsNewNameField.getText();

        if (newName.isEmpty())
            error = "Новое имя не указано";
        else if (newName.equals(getUser().getDisplayName()))
            error = "Новое имя идентично старому";
        else
            try {
                usersService.getUserByDisplayName(newName);
                error = "Это имя уже занято";
            } catch (UserNotFoundException ignored) {
            }

        if (!error.isEmpty()) {
            playSound(SoundTracks.ACCESS_DENIED, .4);
            showError(error);
            return;
        }
        playSound(SoundTracks.CLICK, .4);

        usersService.updateUserDisplayName(getUser(), newName);
        settingsControllers[0].setCurrentValue(newName);
        userDisplayName.setText(newName);
        getUser().setDisplayName(newName);
        toggleChangeNamePane();
        playSound(SoundTracks.BELL, .4);

        showInformation("Имя изменено успешно", string("success"));
    }

    @FXML
    private void changePasswordAction() {
        String error = "";
        String newPassword = settingsNewPasswordField.getText();

        if (newPassword.isEmpty())
            error = "Новый пароль не указан";
        else if (PasswordSecure.verifyPassword(newPassword, getUser().getPassword()))
            error = "Новый пароль идентичен старому";

        if (!error.isEmpty()) {
            playSound(SoundTracks.ACCESS_DENIED, .4);
            showError(error);
            return;
        }
        playSound(SoundTracks.CLICK, .4);

        usersService.updateUserPassword(getUser(), newPassword);
        settingsControllers[1].setCurrentValue(newPassword);
        getUser().setPassword(PasswordSecure.hashPassword(newPassword));
        toggleChangePasswordPane();
        playSound(SoundTracks.BELL, .4);
        showInformation("Пароль изменен успешно", string("success"));
    }

    @FXML
    private void changeAvatarAction() {
        String error = "";
        String newAvatarUrl = settingsNewAvatarField.getText();

        if (newAvatarUrl.isEmpty())
            error = "Новый аватар не указан";
        else if (newAvatarUrl.equals(getUser().getAvatarUrl()))
            error = "Новый аватар идентичен старому";

        if (!error.isEmpty()) {
            playSound(SoundTracks.ACCESS_DENIED, .4);
            showError(error);
            return;
        }
        playSound(SoundTracks.CLICK, .4);

        try {
            usersService.updateUserAvatarUrl(getUser(), newAvatarUrl);
            Avatars.setupAvatar(avatarUrl, settingsNewAvatarField.getText());
            userAvatar.setImage(new Image(
                    Objects.requireNonNull(
                            getClass().getResourceAsStream(
                                    "/drawables/" + newAvatarUrl
                            )
                    )
            ));
            toggleChangeAvatarPane();
            getUser().setAvatarUrl(newAvatarUrl);
            playSound(SoundTracks.BELL, .4);
            showInformation("Аватар изменен успешно", string("success"));
        } catch (IOException ex) {
            playSound(SoundTracks.ERROR, .4);
            showError("Не удалось найти картинку: " + ex.getMessage());
        }
    }

    @FXML
    private void changeDateOfBirthAction() {
        String error = "";
        LocalDate newDateOfBirth = settingsNewDateOfBirthField.getValue();

        if (newDateOfBirth == null)
            error = "Новая дата рождения не указана";
        else if (newDateOfBirth.equals(getUser().getDateOfBirth()))
            error = "Новая дата рождения идентична старой";

        if (!error.isEmpty()) {
            playSound(SoundTracks.ACCESS_DENIED, .4);
            showError(error);
            return;
        }
        playSound(SoundTracks.CLICK, .4);

        usersService.updateUserDateOfBirth(getUser(), newDateOfBirth);
        settingsControllers[3].setCurrentValue(newDateOfBirth);
        toggleChangeDateOfBirthPane();
        getUser().setDateOfBirth(newDateOfBirth);
        playSound(SoundTracks.BELL, .4);
        showInformation("Дата рождения изменена успешно", string("success"));
    }

    public void openFileAction() {
        playSound(SoundTracks.CLICK, .4);
        File selectedFile = FileChoosers.chooseFile();

        if (selectedFile != null) {
            settingsNewAvatarField.setText(selectedFile.getName());
            avatarUrl = selectedFile.getAbsolutePath();
            Avatars.load(selectedFile, settingsNewAvatarField);
            playSound(SoundTracks.LOADED, .4);
        }
    }

    @FXML
    private void toggleNameAction() {
        playSound(SoundTracks.CLICK, .4);
        toggleChangeNamePane();
    }

    @FXML
    private void togglePasswordAction() {
        playSound(SoundTracks.CLICK, .4);
        toggleChangePasswordPane();
    }

    @FXML
    private void toggleDateOfBirthAction() {
        playSound(SoundTracks.CLICK, .4);
        toggleChangeDateOfBirthPane();
    }

    @FXML
    private void toggleAvatarAction() {
        playSound(SoundTracks.CLICK, .4);
        toggleChangeAvatarPane();
    }

    private void toggleChangeNamePane() {
        settingsChangeNamePane.setVisible(!settingsChangeNamePane.isVisible());
    }

    private void toggleChangePasswordPane() {
        settingsChangePasswordPane.setVisible(!settingsChangePasswordPane.isVisible());
    }

    private void toggleChangeAvatarPane() {
        settingsChangeAvatarPane.setVisible(!settingsChangeAvatarPane.isVisible());
    }

    private void toggleChangeDateOfBirthPane() {
        settingsChangeDateOfBirthPane.setVisible(!settingsChangeDateOfBirthPane.isVisible());
    }

    @FXML
    private void onDeleteAccountAction(ActionEvent event) {
        if (showConfirmation(
                "Вы уверены, что хотите удалить аккаунт?", "Удаление аккаунта"
        ) != ButtonType.YES)
            return;

        usersService.deleteUser(getUser());
        setUser(null);
        showInformation("Аккаунт был успешно удален", string("success"));
        playSound(SoundTracks.BELL, .4);
        StageInitializer.setStage(event, Views.SIGNON);
    }
}

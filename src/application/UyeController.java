package application;

import java.net.URL;
import com.veritabanimysql.Util.VeritabanýUtil;

import application.CalisanController.Calisan_Kayitlar;

import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class UyeController {

	Connection baglanti=null;
	PreparedStatement sorguIfadesi=null;
	ResultSet getirilen=null;
	String sql;

	public UyeController() {
			baglanti=VeritabanýUtil.Baglan();
		}
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Uyeler> uye_table;

    @FXML
    private TableColumn<Uyeler, Integer> uyeid;

    @FXML
    private TableColumn<Uyeler, String> uyeismi;

    @FXML
    private TableColumn<Uyeler, String> uyesoyadi;

    @FXML
    private TableColumn<Uyeler, String> uyeyasi;

    @FXML
    private TableColumn<Uyeler, String> uyecinsiyet;

    @FXML
    private TableColumn<Uyeler, String> bilet_id;

    @FXML
    private TableColumn<Uyeler, String> calisan_id;

    @FXML
    private TextField txt_uyeismi;

    @FXML
    private TextField txt_uyesoyismi;

    @FXML
    private RadioButton kiz_radio;

    @FXML
    private RadioButton erkek_radio;

    @FXML
    private Slider uyeyasi_slider;

    @FXML
    private Button uyeekle_buton;

    @FXML
    private Button uyeguncelle_buton;

    @FXML
    private Button uyesil_buton;

    @FXML
    private Label uyeyasi_label;

    ObservableList<Uyeler> veriler;

    
    @FXML
    void uyeekle_buton_click(ActionEvent event) {
    	sql="insert into musteri(musteri_id,mus_isim,mus_soyisim,mus_yas,mus_cinsiyet,yonetici_id,bilet_id) values(?,?,?,?,?,?,?)";
    	try {
    		sorguIfadesi=baglanti.prepareStatement(sql);
    		sorguIfadesi.setString(1, uyeid.getText().trim());
    		sorguIfadesi.setString(2, uyeismi.getText().trim());
    		sorguIfadesi.setString(3, uyesoyadi.getText().trim());
    		sorguIfadesi.setString(4, uyeyasi.getText().trim());
    		sorguIfadesi.setString(5, uyecinsiyet.getText().trim());
    		sorguIfadesi.setString(6, calisan_id.getText().trim());
    		sorguIfadesi.setString(7, bilet_id.getText().trim());
    		
    		sorguIfadesi.executeUpdate();
    		System.out.println("Uye Ekleme Ýþlemi Baþarýlý");
    		
    	}catch(Exception e) {
    		System.out.println("Uye ekleme iþlemi baþarýsýz.");
    	}
    	
    	veriler=FXCollections.observableArrayList();
    	veriler.add(new Uyeler(1,txt_uyeismi.getText(),txt_uyesoyismi.getText(),uyeyasi_label.getText(),uyecinsiyet.getText()
    			,calisan_id.getText(),bilet_id.getText()));
    	uye_table.setItems(veriler);
    	Alert alert=new Alert(AlertType.INFORMATION);
  		alert.setTitle("DÝKKAT!");
  		alert.setHeaderText("Yeni üye ekleme iþlemi baþarýlý.");
  		alert.showAndWait();
    }

    @FXML
    void uyeguncelle_buton_click(ActionEvent event) {
    	sql="update musteri set mus_isim=?,mus_soyisim=?,mus_yas=?,mus_cinsiyet=?,yonetici_id=?,bilet_id=? where musteri_id=1)";
    	try {
    		sorguIfadesi=baglanti.prepareStatement(sql);
    		sorguIfadesi.setString(1, uyeismi.getText().trim());
    		sorguIfadesi.setString(2, uyesoyadi.getText().trim());
    		sorguIfadesi.setString(3, uyeyasi.getText().trim());
    		sorguIfadesi.setString(4, uyecinsiyet.getText().trim());
    		sorguIfadesi.setString(5, calisan_id.getText().trim());
    		sorguIfadesi.setString(6, bilet_id.getText().trim());
    		
    		sorguIfadesi.executeUpdate();
    		System.out.println("Uye Güncelleme Ýþlemi Baþarýlý.");
    	
	}catch(Exception e) {
		System.out.println("Uye Güncelleme Ýþlemi Baþarýsýz.");
	}
    	Alert alert2=new Alert(AlertType.CONFIRMATION);
		alert2.setTitle("DÝKKAT!");
		alert2.setContentText("Emin misiniz ? ");
		alert2.setHeaderText("Seçili kayýt güncelleniyor.");
		
		Uyeler uye=new Uyeler();
		uye=(Uyeler)
				uye_table.getItems().get(uye_table.getSelectionModel().getSelectedIndex());
		int gunceluyeid=uye.getUyeid();
		
		Uyeler gunceluye=new Uyeler(gunceluyeid,uye.getUyeismi(),uye.getUyesoyismi(),
				uye.uyeyasi.getText(),uye.uyecinsiyet.getText(),uye.getCalisan_id(),uye.getBilet_id());
		
		Optional<ButtonType>result=alert2.showAndWait();
		if(result.get()==ButtonType.OK) {
			uye_table.getItems().set(gunceluyeid, gunceluye);
		}
	
    }
    @FXML
    void uyesil_buton_click(ActionEvent event) {
    	try {
    	sql="delete from musteri where  musteri_id=?,mus_isim=?,mus_soyisim=?,mus_yas=?,mus_cinsiyet=?,yonetici_id=?,bilet_id=?";
    	sorguIfadesi=baglanti.prepareStatement(sql);
		sorguIfadesi.setString(1, uyeid.getText().trim());
		sorguIfadesi.setString(2, uyeismi.getText().trim());
		sorguIfadesi.setString(3, uyesoyadi.getText().trim());
		sorguIfadesi.setString(4, uyeyasi.getText().trim());
		sorguIfadesi.setString(5, uyecinsiyet.getText().trim());
		sorguIfadesi.setString(6, calisan_id.getText().trim());
		sorguIfadesi.setString(7, bilet_id.getText().trim());
		
		sorguIfadesi.executeUpdate();
		System.out.println("Uye Silme Ýþlemi Baþarýlý");
		
	}catch(Exception e) {
		System.out.println("Uye Silme Ýþlemi Baþarýsýz.");
	}
    	
    	
    	Alert alert3=new Alert(AlertType.CONFIRMATION);
		alert3.setTitle("DÝKKAT!");
		alert3.setContentText("Onaylýyor musunuz?");
		alert3.setHeaderText("Seçili kayýt siliniyor.");
		Optional<ButtonType>result=alert3.showAndWait();
		if(result.get()==ButtonType.OK) {
			ObservableList<Uyeler>tumkayitlar,secilenkayit;
			tumkayitlar=uye_table.getItems();
			secilenkayit=uye_table.getSelectionModel().getSelectedItems();
			secilenkayit.forEach(tumkayitlar::remove);
		}
    	
    }



    public void biletidgetir(String bilet_id) {
 
        this.bilet_id.toString();
    	this.bilet_id.setText(bilet_id);
    	this.bilet_id.getText();
 
    }
    
    public void calisanidgetir(String calisan_id) {
    	this.calisan_id.toString();
    	this.calisan_id.setText(calisan_id);
    	this.bilet_id.getText();
    	
    }
    
    
    @FXML
    void initialize() {
       uyeyasi_label.setText(String.valueOf(uyeyasi_slider));
       
       ToggleGroup cinsiyetler=new ToggleGroup();
       kiz_radio.setToggleGroup(cinsiyetler);
   	   erkek_radio.setToggleGroup(cinsiyetler);
       
    	veriler=FXCollections.observableArrayList();
    	
    
    	
    	uyeid.setCellValueFactory(new PropertyValueFactory<>("uyeid"));
    	uyeismi.setCellValueFactory(new PropertyValueFactory<>("uyeismi"));
    	uyesoyadi.setCellValueFactory(new PropertyValueFactory<>("uyesoyadi"));
    	uyeyasi.setCellValueFactory(new PropertyValueFactory<>("uyeyasi"));
    	uyecinsiyet.setCellValueFactory(new PropertyValueFactory<>("uyecinsiyet"));
        bilet_id.setCellValueFactory(new PropertyValueFactory<>("bilet_id"));
    	calisan_id.setCellValueFactory(new PropertyValueFactory<>("calisan_id"));
    	
    	uye_table.setItems(veriler);
    	
    	

    }
    
    public class Uyeler{
    	private int uyeid;
    	private String uyeismi;
    	private String uyesoyadi;
    	private Label uyeyasi;
    	private RadioButton uyecinsiyet;
    	public String bilet_id;
    	public String calisan_id;
    	
    	Uyeler(){}
    	
    	Uyeler(int uyeid,String uyeismi,String uyesoyadi,String uyeyasi,String uyecinsiyet,String bilet_id,String calisan_id){
    		this.uyeid=uyeid;
    		this.uyeismi=uyeismi;
    		this.uyesoyadi=uyesoyadi;
    		this.uyeyasi=new Label(uyeyasi);
    		this.uyecinsiyet=new RadioButton(uyecinsiyet);
    		this.bilet_id=bilet_id;
    		this.calisan_id=calisan_id;
    	}
    	
    	public int getUyeid() {
			return uyeid;
		}
		public void setUyeid(int uyeid) {
			this.uyeid = uyeid;
		}
		public String getUyeismi() {
			return uyeismi;
		}
		public void setUyeismi(String uyeismi) {
			this.uyeismi = uyeismi;
		}
		public String getUyesoyismi() {
			return uyesoyadi;
		}
		public void setUyesoyismi(String uyesoyismi) {
			this.uyesoyadi = uyesoyismi;
		}
		public Label getYas() {
			return uyeyasi;
		}
		public void setYas(Label yas) {
			this.uyeyasi = yas;
		}
		public RadioButton getCinsiyet() {
			return uyecinsiyet;
		}
		public void setCinsiyet(RadioButton cinsiyet) {
			this.uyecinsiyet = cinsiyet;
		}
		public String getBilet_id() {
			return bilet_id;
		}
		public void setBilet_id(String bilet_id) {
			this.bilet_id = bilet_id;
		}
		public String getCalisan_id() {
			return calisan_id;
		}
		public void setCalisan_id(String calisan_id) {
			this.calisan_id = calisan_id;
		}
		
    	
    	
    	
    }
    
}


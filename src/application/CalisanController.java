package application;
import com.veritabanimysql.Util.VeritabanýUtil;



import java.sql.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CalisanController {
	Connection baglanti=null;
	PreparedStatement sorguIfadesi=null;
	ResultSet getirilen=null;
	String sql;

	public CalisanController() {
			baglanti=VeritabanýUtil.Baglan();
		}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Calisan_Kayitlar> calisan_table;

    @FXML
    private TableColumn<Calisan_Kayitlar, Integer> calisan_id;

    @FXML
    private TableColumn<Calisan_Kayitlar, String> calisan_adi;

    @FXML
    private TableColumn<Calisan_Kayitlar, String> calisan_soyadi;

    @FXML
    private TableColumn<Calisan_Kayitlar, String> calisan_sube;

    @FXML
    private TextField txt_calisanadi;

    @FXML
    private TextField txt_calisansoyadi;

    @FXML
    private Button calisansil_buton;

    @FXML
    private ComboBox<String> subesecim_combo;

    @FXML
    private Button calisankayit_buton;

    @FXML
    private Button calisanguncelle_buton;
   
    @FXML
    private Button detay_buton;
 
    @FXML
    private Button biletformu_buton;

   
    
    ObservableList<Calisan_Kayitlar>veriler;

    @FXML
    void calisanguncelle_buton_click(ActionEvent event) {
    	sql="update yonetim set isim=?,soyisim=? where yonetici_id=1";
    	try {
        	sorguIfadesi=baglanti.prepareStatement(sql);
    		sorguIfadesi.setString(1, txt_calisanadi.getText().trim());
    		sorguIfadesi.setString(2, txt_calisansoyadi.getText().trim());
    		sorguIfadesi.executeUpdate();
    		System.out.println("Güncelleme Baþarýlý.");
    		
        	}catch(Exception e) {
        		System.out.println("Güncelleme Baþarýsýz.");
        	}
    	
    	Alert alert2=new Alert(AlertType.CONFIRMATION);
		alert2.setTitle("DÝKKAT!");
		alert2.setContentText("Emin misiniz ? ");
		alert2.setHeaderText("Seçili kayýt güncelleniyor.");
		
		Calisan_Kayitlar kayit=new Calisan_Kayitlar();
		kayit=(Calisan_Kayitlar)
		calisan_table.getItems().get(calisan_table.getSelectionModel().getSelectedIndex());
		int guncel_id=kayit.getCalisan_id();
		Calisan_Kayitlar guncelkayit=new Calisan_Kayitlar(guncel_id,kayit.getCalisan_adi(),kayit.getCalisan_soyadi(),kayit.calisan_sube.getSelectionModel().getSelectedItem());
		
		Optional<ButtonType>result=alert2.showAndWait();
		if(result.get()==ButtonType.OK) {
			calisan_table.getItems().set(guncel_id, guncelkayit);
		}
		
    }

    @FXML
    void calisankayit_buton_click(ActionEvent event) {
    	sql="insert into yonetim(isim,soyisim) values(?,?)";
    	try {
    	sorguIfadesi=baglanti.prepareStatement(sql);
		sorguIfadesi.setString(1, txt_calisanadi.getText().trim());
		sorguIfadesi.setString(2, txt_calisansoyadi.getText().trim());
		sorguIfadesi.executeUpdate();
		System.out.println("Ekleme Baþarýlý");
		
    	}catch(Exception e) {
    		System.out.println("Ekleme Baþarýsýz. ");
    	}
    	
    	veriler=FXCollections.observableArrayList();
    	veriler.add(new Calisan_Kayitlar(1, txt_calisanadi.getText(),txt_calisansoyadi.getText(),
    			subesecim_combo.getSelectionModel().getSelectedItem()));
    	  calisan_table.getItems().addAll(veriler);
    	Alert alert=new Alert(AlertType.INFORMATION);
  		alert.setTitle("DÝKKAT!");
  		alert.setHeaderText("Yeni çalýþan ekleme baþarýlý.");
  		alert.showAndWait();
    	
    }

    @FXML
    void calisansil_buton_click(ActionEvent event) {
    	sql="delete from yonetim where isim=? and soyisim=?";
    	try {
        	sorguIfadesi=baglanti.prepareStatement(sql);
    		sorguIfadesi.setString(1, txt_calisanadi.getText().trim());
    		sorguIfadesi.setString(2, txt_calisansoyadi.getText().trim());
    		sorguIfadesi.executeUpdate();
    		System.out.println("Silme Ýþlemi Baþarýlý. ");
    		
        	}catch(Exception e) {
        		System.out.println("Silme Ýþlemi Baþarýsýz. ");
        	}
    	
    	Alert alert3=new Alert(AlertType.CONFIRMATION);
		alert3.setTitle("DÝKKAT!");
		alert3.setContentText("Onaylýyor musunuz?");
		alert3.setHeaderText("Seçili kayýt siliniyor.");
		Optional<ButtonType>result=alert3.showAndWait();
		if(result.get()==ButtonType.OK) {
			ObservableList<Calisan_Kayitlar>tumkayitlar,secilenkayit;
			tumkayitlar=calisan_table.getItems();
			secilenkayit=calisan_table.getSelectionModel().getSelectedItems();
			secilenkayit.forEach(tumkayitlar::remove);
		}
    }
    
    
    public void calisanverileri(TableView<Calisan_Kayitlar> calisan_id) {
    	
    	sql="Select yonetici_id from yonetim";
    	ObservableList<Calisan_Kayitlar>calisanbilgi=FXCollections.observableArrayList();
    	try {
    		sorguIfadesi=baglanti.prepareStatement(sql);
    		ResultSet getirilen=sorguIfadesi.executeQuery();
    		
    		while(getirilen.next()) {
    			   calisanbilgi.add(new Calisan_Kayitlar(getirilen.getInt("calisan_id"),getirilen.getString("isim"),
    					   getirilen.getString("soyisim"),getirilen.getString("sehirler"))); 
    			    
    		     Label calisan_id_label=new Label(calisan_id.toString());
    		     calisan_id_label.getText();	      
    	         	
    		   calisan_table.setItems(calisanbilgi);	
    		   		}}catch(Exception e) {
    			    		System.out.println("hata!");	
    			    	}
    	
    }
    	
    
    @FXML
    void detay_buton_click(ActionEvent event) {
    	try {
   		   
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("istatistik.fxml"));
    		AnchorPane pane4=(AnchorPane)loader.load();
    		Scene scene4=new Scene(pane4);
    		Stage stage4=new Stage();
    		stage4.setScene(scene4);
    		stage4.show();
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		
    	}

    }

    @FXML
    void biletformu_buton_click(ActionEvent event) {
    	try {
  		   
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("Bilet.fxml"));
    		AnchorPane pane3=(AnchorPane)loader.load();
    		Scene scene3=new Scene(pane3);
    		Stage stage3=new Stage();
    		stage3.setScene(scene3);
    		stage3.show();
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		
    	}
    	
    }

   
    @FXML
    void initialize() {
    	calisanverileri(calisan_table);
    	
    	Tooltip tip1=new Tooltip();
    	tip1.setText("Çalýþan kaydý yapmak için týklayýnýz.");
    	tip1.setStyle("-fx-font-style:sans-serif;"+"-fx-background-color:#D2691E;");
    	calisankayit_buton.setTooltip(tip1);
    	
    	Tooltip tip2=new Tooltip();
    	tip2.setText("Çalýþan kaydýný silmek için týklayýnýz.");
    	tip2.setStyle("-fx-font-style:sans-serif;"+"-fx-background-color:#D2691E;");
    	calisansil_buton.setTooltip(tip2);
    	
    	Tooltip tip3=new Tooltip();
    	tip3.setText("Çalýþan kaydýna güncelleme yapmak için týklayýnýz.");
    	tip3.setStyle("-fx-font-style:sans-serif;"+"-fx-background-color:#D2691E;");
    	calisanguncelle_buton.setTooltip(tip3);
    	
    	Tooltip tip4=new Tooltip();
    	tip4.setText("Çalýþan kaydý detaylarýný görüntülemek için týklayýnýz.");
    	tip4.setStyle("-fx-font-style:sans-serif;"+"-fx-background-color:#D2691E;");
    	detay_buton.setTooltip(tip4);
    	
    	Tooltip tip5=new Tooltip();
    	tip5.setText("Satýlan bilet detaylarýný görüntülemek için týklayýnýz.");
    	tip5.setStyle("-fx-font-style:sans-serif;"+"-fx-background-color:#D2691E;");
    	biletformu_buton.setTooltip(tip5);
    	
    	
    	
    	
    	ObservableList<String>sehirler=FXCollections.observableArrayList("Hatay","Adana","Mersin","Ýstanbul","Ýzmir");
        subesecim_combo.getItems().addAll(sehirler);
        subesecim_combo.setItems(sehirler);
        
        veriler=FXCollections.observableArrayList();
        
        calisan_id.setCellValueFactory(new PropertyValueFactory<>("calisan_id"));
        calisan_adi.setCellValueFactory(new PropertyValueFactory<>("calisan_adi"));
        calisan_soyadi.setCellValueFactory(new PropertyValueFactory<>("calisan_soyadi"));	
        calisan_sube.setCellValueFactory(new PropertyValueFactory<>("calisan_sube"));	
        
        calisan_table.setItems(veriler);
        
        try {
 		   
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("Uye.fxml"));
    	
    		UyeController nesne=loader.getController();
    		nesne.calisanidgetir(sql);
    		
    	 
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		
    	}
        
        try {
  		   
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("istatistik.fxml"));
    	
    		istatistikController nesne=loader.getController();
    		nesne.calisanidgetir(sql);
    		
    	 
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		
    	}
        
        
   
       
        
    }
    
    
    
    public class Calisan_Kayitlar{

    	private String calisan_adi;
    	private int calisan_id;
    	private String calisan_soyadi;
        private ComboBox<String> calisan_sube;
    
       
		        
        Calisan_Kayitlar(){}

        ObservableList<String>sehirler=FXCollections.observableArrayList("Hatay","Adana","Mersin","Ýstanbul","Ýzmir");
        Calisan_Kayitlar(int calisan_id,String calisan_adi,String calisan_soyadi,String calisan_sube){
        	this.calisan_adi=calisan_adi;
        	this.calisan_id=calisan_id;
        	this.calisan_soyadi=calisan_soyadi;
        	this.calisan_sube=new ComboBox<String>(sehirler);	
        }
        
       
        public String getCalisan_adi() {
			return calisan_adi;
		}
		public void setCalisan_adi(String calisan_adi) {
			this.calisan_adi = calisan_adi;
		}
		public int getCalisan_id() {
			return calisan_id;
		}
		public void setCalisan_id(int calisan_id) {
			this.calisan_id = calisan_id;
		}
		public String getCalisan_soyadi() {
			return calisan_soyadi;
		}
		public void setCalisan_soyadi(String calisan_soyadi) {
			this.calisan_soyadi = calisan_soyadi;
		}
		public ComboBox<String> getCalisan_sube() {
			return calisan_sube;
		}
		public void setCalisan_sube(ComboBox<String> calisan_sube) {
			this.calisan_sube = calisan_sube;
		}
        
    	
    }
    
    
    
    
}


package application;
import com.veritabanimysql.Util.VeritabanýUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.net.URL;
import java.util.ResourceBundle;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class BiletController {
	Connection baglanti=null;
	PreparedStatement sorguIfadesi=null;
	ResultSet getirilen=null;
	String sql;

	public BiletController() {
			baglanti=VeritabanýUtil.Baglan();
		}
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TableView<Biletler> bilet_table;

    @FXML
    private TableColumn<Biletler, Integer> bilet_id;

    @FXML
    private TableColumn<Biletler, String> koltukno;

    @FXML
    private TableColumn<Biletler, String> kalkisyeri;

    @FXML
    private TableColumn<Biletler, String> varisyeri;

    @FXML
    private TableColumn<Biletler, String> kalkisgunu;

    @FXML
    private TableColumn<Biletler, String> kalkissaati;

    @FXML
    private ComboBox<String> kalkisyeri_combo;

    @FXML
    private ComboBox<String> varisyeri_combo;

    @FXML
    private Spinner<Integer> kalkissaati_spinner;

    @FXML
    private Label kalkissaati_label;

    @FXML
    private DatePicker kalkisgunu_datepicker;

    @FXML
    private Label kalkisgunu_label;

    @FXML
    private Button biletekle_buton;

    @FXML
    private Button biletguncelle_buton;

    @FXML
    private Button biletsil_buton;
    
    @FXML
    private Slider koltukno_slider;

    @FXML
    private Label koltukno_label;
    
    ObservableList<Biletler>veriler;
    String pattern="dd-MM-yyyy";
    


    @FXML
    void biletekle_buton_click(ActionEvent event) {
    	sql="insert into bilet(bilet_id,koltukno,kalkisyeri,varisyeri,kalkissaati,kalkisgunu) values(?,?,?,?,?,?)";
    	try {
    	sorguIfadesi=baglanti.prepareStatement(sql);
    	sorguIfadesi.setString(1, bilet_id.getText().trim());
    	sorguIfadesi.setString(2, koltukno.getText().trim());
		sorguIfadesi.setString(3, kalkisyeri.getText().trim());
		sorguIfadesi.setString(4, varisyeri.getText().trim());
		sorguIfadesi.setString(5, kalkissaati.getText().trim());
		sorguIfadesi.setString(6, kalkisgunu.getText().trim());
		
		sorguIfadesi.executeUpdate();
		System.out.println("Bilet Ekleme Baþarýlý");
		
    	}catch(Exception e) {
    		System.out.println("Bilet Ekleme Baþarýsýz. ");
    	}
 

    	veriler=FXCollections.observableArrayList();
   
     	veriler.add(new Biletler(3, koltukno.getTypeSelector().toString(),kalkisyeri.getText(),varisyeri.getText(),kalkissaati_spinner.getValueFactory().getValue().toString(),
    	kalkisgunu_datepicker.getConverter().toString()));
    	bilet_table.getItems().addAll(veriler);
    	Alert alert=new Alert(AlertType.INFORMATION);
  		alert.setTitle("DÝKKAT!");
  		alert.setHeaderText("Yeni bilet ekleme iþlemi baþarýlý.");
  		alert.showAndWait();
  
    }

    
    @FXML
    void biletguncelle_buton_click(ActionEvent event) {
    	sql="update bilet set koltukno=?,kalkisyeri=?,varisyeri=?,kalkisyeri=?,varisyeri=?,kalkissaati=?,kalkisgunu=? where bilet_id=1";
    	try {
        	sorguIfadesi=baglanti.prepareStatement(sql);
    		sorguIfadesi.setString(1, koltukno.getText().trim());
    		sorguIfadesi.setString(2, kalkisyeri.getText().trim());
    		sorguIfadesi.setString(3, varisyeri.getText().trim());
    		sorguIfadesi.setString(4, kalkissaati.getText().trim());
    		sorguIfadesi.setString(2, kalkisgunu.getText().trim());
    		sorguIfadesi.executeUpdate();
    		System.out.println("Bilet Güncelleme Ýþlemi Baþarýlý.");
    		
        	}catch(Exception e) {
        		System.out.println("Bilet Güncelleme Ýþlemi Baþarýsýz.");
        	}
    	Alert alert2=new Alert(AlertType.CONFIRMATION);
		alert2.setTitle("DÝKKAT!");
		alert2.setContentText("Emin misiniz ? ");
		alert2.setHeaderText("Seçili kayýt güncelleniyor.");
		
		Biletler bilet=new Biletler();
		bilet=(Biletler)
		bilet_table.getItems().get(bilet_table.getSelectionModel().getSelectedIndex());
		int guncel_id=bilet.getBilet_id();
		
		Biletler guncelbilet=new Biletler(guncel_id,bilet.getKoltukno(),bilet.getKalkisyeri().getSelectionModel().getSelectedItem(),
				bilet.getVarisyeri().getSelectionModel().getSelectedItem(),bilet.getKalkissaati().toString(),bilet.getKalkisgunu().getTypeSelector().toString());
		
		Optional<ButtonType>result=alert2.showAndWait();
		if(result.get()==ButtonType.OK) {
			bilet_table.getItems().set(guncel_id, guncelbilet);
		}
		
    }
    
    
    @FXML
    void biletsil_buton_click(ActionEvent event) {
    	sql="delete from bilet where koltukno=?,kalkisyeri=?,varisyeri=?,kalkisyeri=?,varisyeri=?,kalkissaati=?,kalkisgunu=?";
    	try {
        	sorguIfadesi=baglanti.prepareStatement(sql);
        	sorguIfadesi.setString(1, koltukno.getText().trim());
    		sorguIfadesi.setString(2, kalkisyeri.getText().trim());
    		sorguIfadesi.setString(3, varisyeri.getText().trim());
    		sorguIfadesi.setString(4, kalkissaati.getText().trim());
    		sorguIfadesi.setString(2, kalkisgunu.getText().trim());
    		sorguIfadesi.executeUpdate();
    		System.out.println("Bilet Kaydý Silme Ýþlemi Baþarýlý. ");
    		
        	}catch(Exception e) {
        		System.out.println("Bilet Kaydý Silme Ýþlemi Baþarýsýz.");
        	}
    	
    	Alert alert3=new Alert(AlertType.CONFIRMATION);
		alert3.setTitle("DÝKKAT!");
		alert3.setContentText("Onaylýyor musunuz?");
		alert3.setHeaderText("Seçili bilet siliniyor.");
		Optional<ButtonType>result=alert3.showAndWait();
		if(result.get()==ButtonType.OK) {
			ObservableList<Biletler>tumkayitlar,secilenkayit;
			tumkayitlar=bilet_table.getItems();
			secilenkayit=bilet_table.getSelectionModel().getSelectedItems();
			secilenkayit.forEach(tumkayitlar::remove);
		}
    }
    public void biletverileri(TableView<Biletler> bilet_id) {
    	sql="Select bilet_id from bilet";
    	ObservableList<Biletler>biletbilgi=FXCollections.observableArrayList();
    	try {
    		sorguIfadesi=baglanti.prepareStatement(sql);
    		ResultSet getirilen=sorguIfadesi.executeQuery();
    		
    		while(getirilen.next()) {
    			   biletbilgi.add(new Biletler(getirilen.getInt("biled_id"),getirilen.getString("koltukno"),getirilen.getString("kalkisyeri"),getirilen.getString("varisyeri"),
    			   getirilen.getString("kalkissaati"),getirilen.getString("kalkisgunu"))); 
    			    
    		     Label bilet_id_label=new Label(bilet_id.toString());
    		     bilet_id_label.getText();	      
    	         	
    		   bilet_table.setItems(biletbilgi);	
    		}}catch(Exception e) {
    			    		System.out.println("hata!");	
    			    	}
    	
    	}
    
    public void biletverileri2(TableView<Biletler> kalkisyeri) {
    	sql="Select kalkisyeri from bilet";
    	ObservableList<Biletler>biletbilgi=FXCollections.observableArrayList();
    	try {
    		sorguIfadesi=baglanti.prepareStatement(sql);
    		ResultSet getirilen=sorguIfadesi.executeQuery();
    		
    		while(getirilen.next()) {
    			   biletbilgi.add(new Biletler(getirilen.getInt("biled_id"),getirilen.getString("koltukno"),getirilen.getString("kalkisyeri"),getirilen.getString("varisyeri"),
    			   getirilen.getString("kalkissaati"),getirilen.getString("kalkisgunu"))); 
    			    
    		     Label kalkisyeri_label=new Label(kalkisyeri.getSelectionModel().getSelectedItem().toString());
    		     kalkisyeri_label.getText();	      
    	         	
    		   bilet_table.setItems(biletbilgi);	
    		}}catch(Exception e) {
    			    		System.out.println("hata!");	
    			    	}
    	
    	}
    
        
    @FXML
    void initialize() {
    
    	biletverileri(bilet_table);
    	biletverileri2(bilet_table);
    	
    	final int initialValue=1;
    	SpinnerValueFactory<Integer>valueFactory=new SpinnerValueFactory.IntegerSpinnerValueFactory(10,20,initialValue);
        kalkissaati_spinner.setValueFactory(valueFactory);
 
        kalkissaati_label.setText(kalkissaati_spinner.getTypeSelector().toString());
       
    	kalkisgunu_datepicker.setConverter(new StringConverter<LocalDate>(){
      		DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern(pattern);
      	    
      	@Override 
      	public String toString(LocalDate date) {
      		if(date!=null) {
      			return dateFormatter.format(date);
      		}
      		else {
      			
      			return " ";
      		}	
      	}
      	@Override 
      	public LocalDate fromString(String string) {
      		if(string!=null && !string.isEmpty()) {
      			return LocalDate.parse(string,dateFormatter);
      			
      		}
      		else {
      			return null;
      		}
      		
      	}
      	
      	
      	});
    	
    	kalkisgunu_datepicker.setOnAction(event->{
      		LocalDate secilitarih1=kalkisgunu_datepicker.getValue();
      	}
      	);
    	
    	
    	kalkisgunu.setText(kalkisgunu_datepicker.getConverter().toString());
        
    	
    	koltukno_label.setText(String.valueOf(koltukno_slider));
   
    	bilet_id.setCellValueFactory(new PropertyValueFactory<>("bilet_id"));
    	koltukno.setCellValueFactory(new PropertyValueFactory<>("koltukno"));
    	kalkisyeri.setCellValueFactory(new PropertyValueFactory<>("kalkisyeri"));
    	varisyeri.setCellValueFactory(new PropertyValueFactory<>("varisyeri"));
    	kalkisgunu.setCellValueFactory(new PropertyValueFactory<>("kalkisgunu"));
    	kalkissaati.setCellValueFactory(new PropertyValueFactory<>("kalkissaati"));
    	
    	try {
    		   
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("Uye.fxml"));
    		AnchorPane pane1=(AnchorPane)loader.load();
    		
    		UyeController nesne=loader.getController();
    		nesne.biletidgetir(sql);
    		
    	 
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		
    	}
    	
    	try {
 		   
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("istatistik.fxml"));
    		AnchorPane pane2=(AnchorPane)loader.load();
    		
    		istatistikController nesne=loader.getController();
    		nesne.kalkisyerigetir(sql);
    		
    	 
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		
    	}
    	
    	try {
  		   
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("istatistik.fxml"));
    		AnchorPane pane2=(AnchorPane)loader.load();
    		
    		istatistikController nesne=loader.getController();
    		nesne.varisyerigetir(sql);
    		
    	 
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		
    	}

    	
    	
    	
    	}
   
        
   
    public class Biletler{
    	public int bilet_id;
    	private String koltukno;
    	private ComboBox<String>  kalkisyeri;
    	private ComboBox<String> varisyeri;
    	private DatePicker kalkisgunu;
    	private Label kalkissaati_label;
    	
    	
    	
    	
    	ObservableList<String>kalkisyerleri=FXCollections.observableArrayList("Hatay","Adana","Mersin","Ýstanbul","Ýzmir");
    	ObservableList<String>varisyerleri=FXCollections.observableArrayList("Hatay","Adana","Mersin","Ýstanbul","Ýzmir");
    	
        
    
    	Biletler(){}
    	
    	Biletler(int bilet_id,String koltukno,String kalkisyeri,String varisyeri,String kalkisgunu,String kalkissaati_label){
    		this.bilet_id=bilet_id;
    		this.koltukno=koltukno;
    		this.kalkisyeri=new ComboBox<String>(kalkisyerleri);
    		this.varisyeri=new ComboBox<String>(varisyerleri);
    		final int initialValue=1;
        	SpinnerValueFactory<Integer>valueFactory=new SpinnerValueFactory.IntegerSpinnerValueFactory(10,20,initialValue);
            kalkissaati_spinner.setValueFactory(valueFactory);
           
    		this.kalkissaati_label=new Label(kalkissaati_label);
    		
    		
    		this.kalkisgunu=new DatePicker();
    	  
    		
    	}
    	
    	public int getBilet_id() {
			return bilet_id;
		}
		public void setBilet_id(int bilet_id) {
			this.bilet_id = bilet_id;
		}
		public String getKoltukno() {
			return koltukno;
		}
		public void setKoltukno(String koltukno) {
			this.koltukno = koltukno;
		}
		public ComboBox<String> getKalkisyeri() {
			return kalkisyeri;
		}
		public void setKalkisyeri(ComboBox<String> kalkisyeri) {
			this.kalkisyeri = kalkisyeri;
		}
		public ComboBox<String> getVarisyeri() {
			return varisyeri;
		}
		public void setVarisyeri(ComboBox<String> varisyeri) {
			this.varisyeri = varisyeri;
		}
		public DatePicker getKalkisgunu() {
			return kalkisgunu;
		}
		public void setKalkisgunu(DatePicker kalkisgunu) {
			this.kalkisgunu = kalkisgunu;
		}
		public Label getKalkissaati() {
			return kalkissaati_label;
		}
		public void setKalkissaati(Label kalkissaati_label) {
			this.kalkissaati_label = kalkissaati_label;
		}
    	
    	
    }

    
    	}


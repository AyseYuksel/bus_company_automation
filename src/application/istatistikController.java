package application;
import com.veritabanimysql.Util.VeritabanýUtil;

import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class istatistikController {
	Connection baglanti=null;
	PreparedStatement sorguIfadesi=null;
	ResultSet getirilen=null;
	String sql;

	public istatistikController() {
			baglanti=VeritabanýUtil.Baglan();
		} 
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BarChart<String, Integer> satis_bar;

    @FXML
    private CategoryAxis aylar_bar;

    @FXML
    private NumberAxis calisansatis_bar;

    @FXML
    private PieChart kalkis_pie;

    @FXML
    private PieChart varis_pie;

    ObservableList<String>aylar=FXCollections.observableArrayList(
    		"Ocak","Þubat","Mart","Nisan","Mayýs","Haziran","Temmuz","Aðustos","Eylül","Ekim","Kasým","Aralýk");
    ObservableList<PieChart.Data>piekalkis;
    ObservableList<PieChart.Data>pievaris;
	
    private String kalkisyeri; 
	private String varisyeri;
	private String calisan_id;
 

    ObservableList<istatistikler>veriler;
    
    @FXML
    void initialize() {
 
    	
    	String secilisehir = null;
    	int secimsayisi=0;
      	for(istatistikler kayit:veriler) {
      		if(secilisehir==kalkisyeri){
      
      		secimsayisi++;		
      	}
 	}
      	
      	pievaris=FXCollections.observableArrayList();
      	new PieChart.Data("Hatay", secimsayisi);
      	new PieChart.Data("Adana", secimsayisi);
      	new PieChart.Data("Ýstanbul", secimsayisi);
      	new PieChart.Data("Ýzmir", secimsayisi);
      	new PieChart.Data("Mersin", secimsayisi);
    	
    	
      	String secilisehir2 = null;
    	int secimsayisi2=0;
      	for(istatistikler kayit:veriler) {
      		if(secilisehir2==varisyeri){
      
      		secimsayisi2++;		
      	}
 	}
      	
      	pievaris=FXCollections.observableArrayList();
      	new PieChart.Data("Hatay", secimsayisi2);
      	new PieChart.Data("Adana", secimsayisi2);
      	new PieChart.Data("Ýstanbul", secimsayisi2);
      	new PieChart.Data("Ýzmir", secimsayisi2);
      	new PieChart.Data("Mersin", secimsayisi2);
    	

      	
        int[] aysayaci=new int[12];
       	for(String ay:aylar) {
       	int seciliay=aylar.indexOf(ay);	
       	aysayaci[seciliay]++;
       	}
       	XYChart.Series<String, Integer>series=new XYChart.Series<String, Integer>();
      	for(int i=0;i<aysayaci.length;i++) {
      		series.getData().add(new XYChart.Data<String, Integer>(aylar.get(i),aysayaci[i]));
      		
      	}
      	satis_bar.getData().add(series);
   
      	 aylar_bar.setCategories(aylar); 
      	  
      	
      	 
      	String calisan=null;
      	int satissayisi=0;
      	for(istatistikler kayit:veriler) {
      		if(calisan_id==calisan){
      		satissayisi++;		
      	}
 	}
     
      	
      	
    }

  
    
       public class istatistikler{
	
   	   private String calisan_id;
        private String kalkisyeri;
	   	private String varisyeri;
	       
	istatistikler(){}       
	       
	    istatistikler(String calisan_id,String kalkisyeri,String varisyeri) 
	    {
	    	this.calisan_id=calisan_id;
	    	this.varisyeri=varisyeri;
	    	this.kalkisyeri=kalkisyeri;
	    }
	  
	       
	    public String getCalisan_id() {
			return calisan_id;
		}
		public void setCalisan_id(String calisan_id) {
			this.calisan_id = calisan_id;
		}
		public String getKalkisyeri() {
			return kalkisyeri;
		}
		public void setKalkisyeri(String kalkisyeri) {
			this.kalkisyeri = kalkisyeri;
		}
		public String getVarisyeri() {
			return varisyeri;
		}
		public void setVarisyeri(String varisyeri) {
			this.varisyeri = varisyeri;
		}
	
	       
	
	
}
	public void kalkisyerigetir(String kalkisyeri) {
		this.kalkisyeri=kalkisyeri;
		
	}

	public void varisyerigetir(String varisyeri) {
		this.varisyeri=varisyeri;
		
	}

	public void calisanidgetir(String calisan_id) {
	 this.calisan_id=calisan_id;
		
	}
	
 }

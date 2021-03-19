package application;
import com.veritabanimysql.Util.VeritabanýUtil;
import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SampleController {
	Connection baglanti=null;
	PreparedStatement sorguIfadesi=null;
	ResultSet getirilen=null;
	String sql;

	public SampleController() {
			baglanti=VeritabanýUtil.Baglan();
		}
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField kul_adi;

    @FXML
    private TextField kul_sifre;

    @FXML
    private Button uyegirisi_buton;

    @FXML
    private Button yoneticigirisi_buton;

    @FXML
    private Button ekle_buton;

    @FXML
    private Button sil_buton;

    @FXML
    void ekle_buton_click(ActionEvent event) {
    	sql="insert into login(kul_ad,sifre) values(?,?)";
    	try {
    	sorguIfadesi=baglanti.prepareStatement(sql);
		sorguIfadesi.setString(1, kul_adi.getText().trim());
		sorguIfadesi.setString(2, kul_sifre.getText().trim());
		sorguIfadesi.executeUpdate();
		System.out.println("ekleme baþarýlý");
	
		
    	}catch(Exception e) {
    		System.out.println("ekleme baþarýsýz");
    	}
    	

    }

    @FXML
    void sil_buton_click(ActionEvent event) {
    	sql="delete from login where kul_ad=? and sifre=?";
    	try {
        	sorguIfadesi=baglanti.prepareStatement(sql);
    		sorguIfadesi.setString(1, kul_adi.getText().trim());
    		sorguIfadesi.setString(2, kul_sifre.getText().trim());
    		sorguIfadesi.executeUpdate();
    		System.out.println("silme iþlemi baþarýlý");
    		
        	}catch(Exception e) {
        		System.out.println("silme iþlemi baþarýsýz");	
        	}
    }

    @FXML
    void uyegirisi_buton_click(ActionEvent event) {
    	try {
    	
    		FXMLLoader loader=new FXMLLoader(getClass().getResource("Uye.fxml"));
    		AnchorPane pane1=(AnchorPane)loader.load();
    		Scene scene1=new Scene(pane1);
    		Stage stage1=new Stage();
    		stage1.setScene(scene1);
    		stage1.show();
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		
    	}
    	
    	
   	}
    

    @FXML
    void yoneticigirisi_buton_click(ActionEvent event) {
    	 try {
    		
     		FXMLLoader loader=new FXMLLoader(getClass().getResource("Calisan.fxml"));
     		AnchorPane pane2=(AnchorPane)loader.load();
     		Stage stage2=new Stage();
    		Scene scene2=new Scene(pane2);
     		stage2.setScene(scene2);
     		stage2.show();
     		
     	}catch(Exception e) {
     		e.printStackTrace();
     		
     	}
    }

    @FXML
    void initialize() {
    	Tooltip tip1=new Tooltip();
    	tip1.setText("Kullanýcý giriþi yapmak için týklayýnýz.");
    	tip1.setStyle("-fx-font-style:sans-serif;"+"-fx-background-color:#D2691E;");
    	ekle_buton.setTooltip(tip1);
    	
    	
    	Tooltip tip2=new Tooltip();
    	tip2.setText("Kullanýcýyý silmek için týklayýnýz. ");
    	tip2.setStyle("-fx-font-style:sans-serif;"+"-fx-background-color:#D2691E;");
    	sil_buton.setTooltip(tip2);
    	
    	
    	Tooltip tip3=new Tooltip();
    	tip3.setText("Üye bilgileri içeren forma eriþmek için týklayýnýz.");
    	tip3.setStyle("-fx-font-style:sans-serif;"+"-fx-background-color:#D2691E;");
        uyegirisi_buton.setTooltip(tip3);
        
        
        Tooltip tip4=new Tooltip();
        tip4.setText("Kullanýcý bilgileri içeren forma eriþmek için týklayýnýz.");
    	tip4.setStyle("-fx-font-style:sans-serif;"+"-fx-background-color:#D2691E;");
        yoneticigirisi_buton.setTooltip(tip4);

    }
}
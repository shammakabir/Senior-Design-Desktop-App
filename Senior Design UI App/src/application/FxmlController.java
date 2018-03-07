package application;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.FileChooser;

public class FxmlController implements Initializable {

	@FXML
	private ColorPicker colorpicker;

	@FXML
	private Slider bsize;

	@FXML
	private Canvas canvas;
	
	@FXML
	private MenuBar menubar;
	
	@FXML
	private MenuItem open;

	@FXML
	private ImageView template;

	GraphicsContext brushTool;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		bsize.setMin(5);
		bsize.setMax(100);
		canvas.toFront();
		
		open.setOnAction(
            new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(final ActionEvent e)
                {
                	FileChooser chooser = new FileChooser();
                    File file = chooser.showOpenDialog(menubar.getScene().getWindow());
                    if (file != null)
                    {
                    	try
                    	{
    						BufferedImage bufferedImage = ImageIO.read(file);
    						Image image = SwingFXUtils.toFXImage(bufferedImage, null);
    	                    template.setImage(image);
    					}
                    	catch (IOException e1) {
    						e1.printStackTrace();
    					}
                    }
                }
            });

		brushTool = canvas.getGraphicsContext2D();
		canvas.setOnMouseDragged(e -> {
			double size = bsize.valueProperty().get();
			double x = e.getX() - size / 2;
			double y = e.getY() - size / 2;

			brushTool.setFill(colorpicker.getValue());
			brushTool.fillRoundRect(x, y, size, size, size, size);
		});

	}
	
	public static FileChooser createFileChooser() {
        FileChooser chooser = new FileChooser();
        //chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        return chooser ;
    }

}

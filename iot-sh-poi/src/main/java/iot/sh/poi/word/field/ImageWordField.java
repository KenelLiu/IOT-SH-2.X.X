package iot.sh.poi.word.field;

import lombok.Data;
import java.io.File;

/**
 * @author Kenel Liu
 */
@Data
public class ImageWordField extends WordField{
    private File picture;
    private int  pictureType;//XWPFDocument.PICTURE_TYPE_JPEG;
    private String pictureName;

    private int width;
    private int height;
}

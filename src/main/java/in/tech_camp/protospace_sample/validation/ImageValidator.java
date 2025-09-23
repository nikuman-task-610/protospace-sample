package in.tech_camp.protospace_sample.validation;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {
    
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    
    @Override
    public void initialize(ValidImage constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        // ファイルが選択されていない場合
        if (file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("画像ファイルは必須です")
                   .addConstraintViolation();
            return false;
        }
        
        // ファイルサイズチェック
        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("画像ファイルのサイズは10MB以下にしてください")
                   .addConstraintViolation();
            return false;
        }
        
        // ファイル形式チェック（画像ファイルかどうか）
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("画像ファイルを選択してください")
                   .addConstraintViolation();
            return false;
        }
        
        return true;
    }
}

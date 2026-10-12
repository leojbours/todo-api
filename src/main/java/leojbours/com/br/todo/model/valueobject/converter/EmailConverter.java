package leojbours.com.br.todo.model.valueobject.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import leojbours.com.br.todo.model.valueobject.Email;

@Converter
public class EmailConverter implements AttributeConverter<Email, String> {

  @Override
  public String convertToDatabaseColumn(Email email) {
    return email.email();
  }

  @Override
  public Email convertToEntityAttribute(String dbData) {
    return new Email(dbData);
  }
}

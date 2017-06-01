/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Camila
 */
@FacesConverter(value = "localDateConverter")
public class LocalDateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(
                    value,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        } catch (DateTimeParseException e) {
            throw new ConverterException("O ano deve conter 4 dígitos. Exemplo: 13/11/2015.");
        }

        return localDate;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (null == value) {
            return "";
        }
        return ((LocalDate) value).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}

package com.david.demo.configuration;

import com.david.demo.types.SortOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomConverters implements WebMvcConfigurer {

    // Custom converter added to handle if user gives an invalid sortOrder. In that case we
    // default to ascending sort order (instead of giving a 'bad request' response).
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, SortOrder>() {
                                  @Override
                                  public SortOrder convert(String source) {
                                      try {
                                          return SortOrder.valueOf(source);
                                      } catch (IllegalArgumentException e) {
                                          return SortOrder.ASC;
                                      }
                                  }
                              }
        );
    }
}

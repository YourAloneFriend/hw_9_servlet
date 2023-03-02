package org.application.engine;

import lombok.Getter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;

@Getter
public class Engine {
    private static Engine engine;

    private final TemplateEngine templateEngine;

    private Engine(){
        templateEngine = new TemplateEngine();

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setCacheable(false);
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setOrder(1);

        templateEngine.setTemplateResolver(templateResolver);
    }

    public static Engine initialize(){
        if(engine == null)
            return new Engine();
        return engine;
    }
}

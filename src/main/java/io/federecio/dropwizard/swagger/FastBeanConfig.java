package io.federecio.dropwizard.swagger;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.google.common.reflect.TypeToken;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.config.BeanConfig;

public class FastBeanConfig extends BeanConfig{
	
	@Override
    public Set<Class<?>> classes() {
        ConfigurationBuilder config = new ConfigurationBuilder();
        Set<String> acceptablePackages = new HashSet<String>();

        boolean allowAllPackages = false;

        if (getResourcePackage() != null && !"".equals(getResourcePackage())) {
            String[] parts = getResourcePackage().split(",");
            for (String pkg : parts) {
                if (!"".equals(pkg)) {
                    acceptablePackages.add(pkg);
                    config.addUrls(ClasspathHelper.forPackage(pkg));
                }
            }
        } else {
            allowAllPackages = true;
        }

        config.setExpandSuperTypes(getExpandSuperTypes());

        config.setScanners(new ResourcesScanner(), new TypeAnnotationsScanner(), new SubTypesScanner());
        
        /*
         * set input filter: speed up scanning and remove warnings on fat jars
         */
        if (!allowAllPackages) {
        	config.setInputsFilter(new FilterBuilder().includePackage(
        			acceptablePackages.toArray(new String[acceptablePackages.size()])));
        }
        

        final Reflections reflections = new Reflections(config);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(javax.ws.rs.Path.class);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(SwaggerDefinition.class);
				classes.addAll(typesAnnotatedWith);
        
        /*
         * Find concrete types annotated with @Api, but with a supertype annotated with @Path.
         * This would handle split resources where the interface has jax-rs annotations
         * and the implementing class has Swagger annotations 
         */
        for (Class<?> cls : reflections.getTypesAnnotatedWith(Api.class)) {
        	for (Class<?> intfc : TypeToken.of(cls).getTypes().interfaces().rawTypes()) {
        		Annotation ann = intfc.getAnnotation(javax.ws.rs.Path.class);
        		if (ann != null) {
        			classes.add(cls);
        			break;
        		}
					}
				}
        
        Set<Class<?>> output = new HashSet<Class<?>>();
        for (Class<?> cls : classes) {
            if (allowAllPackages) {
                output.add(cls);
            } else {
                for (String pkg : acceptablePackages) {
                    // startsWith allows everything within a package
                    // the dots ensures that package siblings are not considered
                    if ((cls.getPackage().getName() + ".").startsWith(pkg + ".")) {
                        output.add(cls);
			break;
                    }
                }
            }
        }
        return output;
    }

}

package software.simple.solutions.data.entry.es.control.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import software.simple.solutions.framework.core.config.AspectBeforeAdvice;

@Aspect
@Configuration
public class EsControlUserAuditAspect extends AspectBeforeAdvice {

	@Before("execution(* software.simple.solutions.data.entry.es.control.service.impl.*Service.*(..))")
	public void before(JoinPoint joinPoint) {

		// Advice
		advice();
	}

}

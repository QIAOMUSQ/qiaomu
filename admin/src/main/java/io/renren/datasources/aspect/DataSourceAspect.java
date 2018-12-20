package io.renren.datasources.aspect;

import io.renren.datasources.DataSourceNames;
import io.renren.datasources.DynamicDataSource;
import io.renren.datasources.ReflectUtil;
import io.renren.datasources.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源，切面处理类
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017/9/16 22:20
 */
@Aspect
@Component
public class DataSourceAspect  {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(io.renren.datasources.annotation.DataSource)"+
            "||@within(io.renren.datasources.annotation.DataSource)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String resultDS = determineDatasource(point);
        if(resultDS.equals("first") || resultDS == null){
            DynamicDataSource.setDataSource(DataSourceNames.FIRST);
            logger.debug("set datasource is " + DataSourceNames.FIRST);
        }else {
            DynamicDataSource.setDataSource(DataSourceNames.SECOND);
            logger.debug("set datasource is " + DataSourceNames.SECOND);
        }

        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            logger.debug("clean datasource");
        }
    }



    /**
     *
     * @param jp
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected String determineDatasource(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Class targetClass = jp.getSignature().getDeclaringType();
        String dataSourceForTargetClass = resolveDataSourceFromClass(targetClass);
        String dataSourceForTargetMethod = resolveDataSourceFromMethod(targetClass, methodName);
        String resultDS = determinateDataSource(dataSourceForTargetClass, dataSourceForTargetMethod);
        return resultDS;
    }


    /**
     * 寻找类上数据源
     * @param targetClass
     * @return
     */
    private String resolveDataSourceFromClass(Class targetClass) {
        DataSource classAnnotation = (DataSource) targetClass.getAnnotation(DataSource.class);
        return null != classAnnotation ? classAnnotation.name() : null;
    }

    /**
     * 寻找方法上设置的数据源
     *
     * @param targetClass
     * @param methodName
     * @return
     */
    @SuppressWarnings("rawtypes")
    private String resolveDataSourceFromMethod(Class targetClass, String methodName) {
        Method m = ReflectUtil.findUniqueMethod(targetClass, methodName);
        if (m != null) {
            DataSource choDs = m.getAnnotation(DataSource.class);
            return choDs == null ? null : choDs.name();
        }
        return null;
    }

    /**
     * 最终数据源，如果方法上设置有数据源，则以方法上的为准，如果方法上没有设置，则以类上的为准，如果类上没有设置，则使用默认数据源
     *
     * @param classDS
     * @param methodDS
     * @return
     */
    private String determinateDataSource(String classDS, String methodDS) {
        return methodDS == null ? classDS : methodDS;
    }

}

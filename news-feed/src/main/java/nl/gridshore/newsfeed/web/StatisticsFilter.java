package nl.gridshore.newsfeed.web;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class StatisticsFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(StatisticsFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        CacheManager cacheManager = CacheManager.getInstance();
        if (cacheManager != null) {
            Ehcache cache = cacheManager.getEhcache("CachePage1CachingFilter");
            Statistics statistics = cache.getStatistics();

            log.info("uri : {}, average get time : {} ms", ((HttpServletRequest)req).getRequestURI(),statistics.getAverageGetTime());
        } else {
            log.debug("No caching manager available");
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        log.debug("init the statistics filter");
    }

}

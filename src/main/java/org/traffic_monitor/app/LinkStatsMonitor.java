package org.traffic_monitor.app;

import org.onosproject.net.Link;
import org.onosproject.net.link.LinkService;
import org.onosproject.net.statistic.Load;
import org.onosproject.net.statistic.StatisticService;

import java.util.LinkedList;
import java.util.List;

public class LinkStatsMonitor {

    private LinkService linkService;
    private StatisticService statisticService;

    public LinkStatsMonitor(LinkService linkService, StatisticService statisticService){
        this.linkService = linkService;
        this.statisticService = statisticService;
    }

    /*
        link stats format:
        {
            {
                src:"001",
                dst:"009",
                rate:"345.67", // in bytes/s
                time:234523542
            },
            {
                src:"001",
                dst:"009",
                rate:"345.67",
                time:234523542
            },
        }
     */
    public String getStats(){
        List<LinkStats> linkStatsList = new LinkedList<>();
        Iterable<Link> links = linkService.getActiveLinks();
        for(Link link: links){
            Load load = statisticService.load(link);
            linkStatsList.add(new LinkStats(
                    link.src().elementId().toString(),
                    link.dst().elementId().toString(),
                    load.rate(),
                    load.time()
            ));
        }
        return linkStatsList.toString();
    }

}

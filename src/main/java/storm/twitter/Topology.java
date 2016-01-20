package storm.twitter;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;


public class Topology {
	  private static final int TOP_N = 50;

	public static void main(String[] args) throws Exception{

		TopologyBuilder builder = new TopologyBuilder();
	    String spoutId = "wordGenerator";
	    String splitId = "split";
	    String counterId = "counter";
	    String intermediateRankerId = "intermediateRanker";
	    String totalRankerId = "finalRanker";

	    builder.setSpout(spoutId, new TwitterSpout(), 1);
	    builder.setBolt(splitId, new SplitBolt(), 3).shuffleGrouping(spoutId);
	    builder.setBolt(counterId, new RollingCountBolt(3600, 60), 3).fieldsGrouping(splitId, new Fields("word"));
	    builder.setBolt(intermediateRankerId, new IntermediateRankingsBolt(TOP_N), 3).fieldsGrouping(counterId, new Fields("obj"));
	    builder.setBolt(totalRankerId, new TotalRankingsBolt(TOP_N)).globalGrouping(intermediateRankerId);

		    Config conf = new Config();
		    conf.setDebug(true);

		    if (args != null && args.length > 0) {
		      conf.setNumWorkers(3);

		      StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
		    }
		    else {
		      conf.setMaxTaskParallelism(3);

		      LocalCluster cluster = new LocalCluster();
		      cluster.submitTopology("word-count", conf, builder.createTopology());
		 }
	}

}

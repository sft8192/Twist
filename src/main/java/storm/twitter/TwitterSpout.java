package storm.twitter;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

@SuppressWarnings("serial")
public class TwitterSpout extends BaseRichSpout {

	SpoutOutputCollector _collector;
	LinkedBlockingQueue<Status> queue = null;
	TwitterStream _twitterStream;

	public TwitterSpout() {
		// TODO Auto-generated constructor stub
	}

	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		queue = new LinkedBlockingQueue<Status>(1000);
		_collector = collector;

		StatusListener listener = new StatusListener() {

			public void onStatus(Status status) {

				queue.offer(status);
			}

			public void onDeletionNotice(StatusDeletionNotice sdn) {
			}

			public void onTrackLimitationNotice(int i) {
			}

			public void onScrubGeo(long l, long l1) {
			}

			public void onException(Exception ex) {
			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}

		};

		TwitterStream twitterStream = new TwitterStreamFactory(
				new ConfigurationBuilder().setJSONStoreEnabled(true).build())
				.getInstance();

		twitterStream.addListener(listener);

			twitterStream.sample();


	}

	public void nextTuple() {
		Status ret = queue.poll();
		if (ret == null) {
			Utils.sleep(50);
		} else {
	        if (containsNihongo(ret.getText()) == true){
			String strText = ret.getText();
	        strText = strText.replaceAll("\r\n","");
	        strText = strText.replaceAll("\r","");
	        strText = strText.replaceAll("\n","");
	        strText = strText.replaceAll("\t","");
	        strText = strText.replaceAll("http(s*)://(.*)/","");
	        /*
	        strText = strText.replaceAll("¥¥uff57", "");
	        strText = strText.replaceAll("RT", "");
	        strText = strText.replaceAll("画像", "");
	        strText = strText.replaceAll("動画", "");
	        strText = strText.replaceAll("定期", "");
	        strText = strText.replaceAll("自分", "");
	        strText = strText.replaceAll("bot", "");
	        strText = strText.replaceAll("http","");
	        strText = strText.replaceAll("ww(w*)","");
	        strText = strText.replaceAll("相互", "");
	        strText = strText.replaceAll("無料", "");
	        strText = strText.replaceAll("rt", "");
	        strText = strText.replaceAll("フォロー", "");
	        strText = strText.replaceAll("フォロワー", "");
	        strText = strText.replaceAll("セフレ", "");
	        strText = strText.replaceAll("at", "");
	        strText = strText.replaceAll("ch", "");
	        strText = strText.replaceAll("出会い", "");
	        strText = strText.replaceAll("感じ", "");
	        strText = strText.replaceAll("恋人", "");
	        strText = strText.replaceAll("LINE", "");
	        strText = strText.replaceAll("ランキング", "");
	        strText = strText.replaceAll("メル", "");
	        strText = strText.replaceAll("モンストキャンペーン", "");
	        strText = strText.replaceAll("ツイート", "");
	        strText = strText.replaceAll("rzzp", "");
	        strText = strText.replaceAll("エロ", "");
	        strText = strText.replaceAll("人気", "");
	        strText = strText.replaceAll("気持ち", "");
	        strText = strText.replaceAll("女の子", "");
	        strText = strText.replaceAll("sougofollow", "");
	        strText = strText.replaceAll("あと", "");
	        strText = strText.replaceAll("アプリ", "");
	        strText = strText.replaceAll("ｗｗｗ", "");
	        strText = strText.replaceAll("followmejp", "");

	        strText = strText.replaceAll("友達", "");
	        strText = strText.replaceAll("写真", "");
	        strText = strText.replaceAll("ーー", "");
	        strText = strText.replaceAll("リプ", "");
	        strText = strText.replaceAll("情報", "");
	        strText = strText.replaceAll("楽しみ", "");
	        strText = strText.replaceAll("DM", "");
	        strText = strText.replaceAll("記事", "");
	        strText = strText.replaceAll("まとめ", "");
	        strText = strText.replaceAll("人人", "");
*/
			_collector.emit(new Values(strText));

		}
		}
	}
    public static boolean containsNihongo(String str) {
        for(int i = 0 ; i < str.length() ; i++) {
            char ch = str.charAt(i);
            Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
            if (Character.UnicodeBlock.HIRAGANA.equals(unicodeBlock))
                return true;
            if (Character.UnicodeBlock.KATAKANA.equals(unicodeBlock))
                return true;
            if (Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS.equals(unicodeBlock))
                return true;
            if (Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(unicodeBlock))
                return true;
            if (Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION.equals(unicodeBlock))
                return true;
        }

        return false;
    }

	@Override
	public void close() {
		//_twitterStream.shutdown();
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		Config ret = new Config();
		ret.setMaxTaskParallelism(1);
		return ret;
	}

	@Override
	public void ack(Object id) {
	}

	@Override
	public void fail(Object id) {
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}
}
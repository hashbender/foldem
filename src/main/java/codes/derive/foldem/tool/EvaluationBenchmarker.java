package codes.derive.foldem.tool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import codes.derive.foldem.Deck;
import codes.derive.foldem.Hand;
import codes.derive.foldem.board.Board;
import codes.derive.foldem.board.Boards;
import codes.derive.foldem.eval.DefaultEvaluator;
import codes.derive.foldem.eval.Evaluator;

public class EvaluationBenchmarker {
	
	private final Evaluator evaluator;
	
	private final int attempts;
	
	private final int threadCount;
	
	public static void main(String... args) {
		BenchmarkResults r = new EvaluationBenchmarker(new DefaultEvaluator(), 1, 1).benchmark();
		
		double perSecond = r.attempted / r.elapsed();
		System.out.println(perSecond + " evaluations per second");
	}
	
	public EvaluationBenchmarker(Evaluator evaluator, int threadCount, int attempts) {
		this.evaluator = evaluator;
		this.threadCount = threadCount;
		this.attempts = attempts;
		
	}
	
	public BenchmarkResults benchmark() {
		final BenchmarkResults results = new BenchmarkResults();

		results.start = System.currentTimeMillis();
		
		
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		
		
		final Hand hand = new Hand("AcAd");
		final Board board = Boards.board("2s7h9c4dAh");
		for (int i = 0; i < threadCount; i++) {
			
			executor.execute(new Runnable() {
				@Override
				public void run() {
					while (true) {
						synchronized (results) {
							if (results.attempted >= attempts) {
								break;
							}
							results.attempted++;
						}
						evaluator.rank(hand, board);
					}
				}
			});
		}
		try {
			executor.awaitTermination(System.currentTimeMillis(), TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		results.finish = System.currentTimeMillis();
		
		
		return results;
	}
	
	public class BenchmarkResults {
		
		private long start;
		private long finish;
		
		private int attempted = 0;
		
		private BenchmarkResults() {
			
		}
		
		public long elapsed() {
			return finish - start;
		}
		
		@Override
		public String toString() {
			return "TODO";
		}
		
	}
	
}

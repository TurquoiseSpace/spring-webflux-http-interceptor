package open.source.exchange.utility.asynchronous;

import org.jboss.logging.MDC;

import lombok.extern.log4j.Log4j2;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Log4j2
public class MonoCallSynchronousExecutor {

	private static final int THRESHOLD_SECONDS = 5;

	private static Thread getHelperThread(Disposable disposable, String descriptor, long perodicCheckMilliSecondsTime) {

		Object requestId = MDC.get("requestId");
		String id = (null != requestId) ? (String) requestId : "";
		Thread helperThread = new Thread() {

			private void checkThresholdCrossed(int sleptTimes, long perodicCheckMilliSeconds) {

				int sleptDurationInSeconds = (int) ((perodicCheckMilliSeconds * sleptTimes) / 1000);
				if (sleptDurationInSeconds > 0 && 0 == sleptDurationInSeconds % THRESHOLD_SECONDS ) {
					log.warn("{} -> (disposed) {} (eachSleepMilliSecondDuration) {} (sleptTimes) {} (sleptDurationInSeconds) {}",
							descriptor, false, perodicCheckMilliSeconds, sleptTimes, sleptDurationInSeconds);
				} else {
					log.debug("{} -> (disposed) {} (eachSleepMilliSecondDuration) {} (sleptTimes) {}",
							descriptor, false, perodicCheckMilliSeconds, sleptTimes);
				}
			}

			@Override
			public void run() {

				MDC.put("requestId", id);
				boolean disposed = false;
				int sleptTimes = 0;
				while (false == (disposed = disposable.isDisposed())) {
					try {
						this.sleep(perodicCheckMilliSecondsTime);
					} catch (InterruptedException e) {
						log.error("InterruptedException -> {}", e.getMessage(), e);
						break;
					}
					++sleptTimes;
					checkThresholdCrossed(sleptTimes, perodicCheckMilliSecondsTime);
				}
				log.debug("{} -> (disposed) {}", descriptor, disposed);
			}

		};
		helperThread.setName("ThreadHelper - " + descriptor);
		return helperThread;
	}

	public static <T> Thread waitForIt(Mono<T> monoCall, String descriptor, long sleepMilliSeconds) {

		Disposable disposable = monoCall.subscribe();
		Thread helperThread = getHelperThread(disposable, descriptor, sleepMilliSeconds);
		helperThread.start();
		// helperThread.join();
		return helperThread;
	}

}

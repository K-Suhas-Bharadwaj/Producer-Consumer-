class ProduceAndConsume {
	
	int n = 0;
	boolean consume = false;
	
	synchronized public void produce(int product) {
		
		while(consume)
			try {
				wait();
			}catch(InterruptedException ie) {
				System.out.println(ie.getMessage());
			}
		
		consume = true;
		
		n = product;
		System.out.println("Produced : "+ n);
		
		notify();
		
	}
	
	synchronized public void consume() {
		
		while(!consume)
			try {
				wait();
			}catch(InterruptedException ie) {
				System.out.println(ie.getMessage());
			}
		
		consume = false;
		
		System.out.println("Consumed : "+n);
		System.out.println();
		
		notify();
		
	}
	
}

class Producer implements Runnable{
	
	ProduceAndConsume producer ;
	Thread producerThread;

	public Producer(ProduceAndConsume obj){
		
		producer = obj;
		producerThread = new Thread(this);
		producerThread.start();
		
	}
	
	@Override
	public void run() {
		
		int i=0;
		
		while(i<=10)
			producer.produce(i++);
		
	}
	
}

class Consumer implements Runnable{
	
	ProduceAndConsume consumer;
	Thread consumerThread;
	
	public Consumer(ProduceAndConsume obj){
		
		consumer = obj;
		consumerThread = new Thread(this);
		consumerThread.start();
		
	}
	
	@Override
	public void run() {

		while(true)
			consumer.consume();
		
	} 
	
}

public class ProducerConsumer {

	
	public static void main(String[] args) {
		
				ProduceAndConsume obj = new ProduceAndConsume();
				
				Producer producer = new Producer(obj);
				Consumer consumer = new Consumer(obj);
				
				try {
					producer.producerThread.join();
					consumer.consumerThread.join();
				}catch(InterruptedException ie) {
					System.out.println(ie.getMessage());
				}
				
	}
		
}

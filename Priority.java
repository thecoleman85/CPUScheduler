import java.util.TreeSet;

public class Priority extends Algorithm {
  private GrantChart grantChart;
  
	public Priority(Process[] processes) {
		super(processes);
	}

	@Override
	protected void sort() {
		if (sorted_processes == null) {
			sorted_processes = new TreeSet<>(new PriorityComparator());
		} else {
			sorted_processes.clear();
		}
		for(Process process : processes) {
			if(process.getArrivingTime() <= time && !grantChart.isProcessStarted(process)) {
			  sorted_processes.add(process);
			}
		}
		
	}

	@Override
	protected void apply() {
		grantChart = new GrantChart();
		time = 0;
		total_waiting_time = 0;
		average_waiting_time = 0;
		sort();
		
		do {
			grantChart.schedule(sorted_processes.first());
			grantChart.PassTime(sorted_processes.first().getBurstTime());
			time = grantChart.getTime();
			sort();
		} while (sorted_processes.size() > 0);
		grantChart.calculateWaitingTime();
		grantChart.print();
		average_waiting_time = grantChart.getTotal_waiting_time() / processes.length;
		
	}

	@Override
	public void printResult() {
		if (sorted_processes == null) {
			apply();
		}
		
		System.out.println();
		System.out.print(Process.getHeader());
		System.out.println("\tPriority");
		
		for(Process process : processes) {
			System.out.print(process);
			System.out.printf("\t%d\n", process.getPriority());
			
		}
		
		System.out.printf("%s Average waiting time: %.2f\n", getName(), average_waiting_time);
		
	}

}

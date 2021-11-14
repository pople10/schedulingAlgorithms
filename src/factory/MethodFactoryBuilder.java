package factory;

import algorithms.FifoAlgorithm;
import algorithms.InterfaceAlgorithm;
import algorithms.RRAlgorithm;
import algorithms.SJF_NOPAlgorithm;
import algorithms.SJF_PAlgorithm;
import algorithms.SRTAlgorithm;
import utility.Methods;

public class MethodFactoryBuilder {
	public static InterfaceAlgorithm build(Methods method,int qtm)
	{
		if(method==Methods.FIFO)
			return new FifoAlgorithm();
		if(method==Methods.RR)
			return new RRAlgorithm(qtm);
		if(method==Methods.SJF_NOP)
			return new SJF_NOPAlgorithm();
		if(method==Methods.SJF_P)
			return new SJF_PAlgorithm();
		if(method==Methods.SRT)
			return new SRTAlgorithm();
		return null;
	}
}

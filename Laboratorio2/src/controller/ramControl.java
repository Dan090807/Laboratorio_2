package controller;

import java.util.ArrayList;
import model.ram;
import model.program;
import java.lang.Math;
import ui.simulationWindow;

public class ramControl {
	
	private ram actualRam;

	// Converting the memory from MB to blocks.
	
	public ramControl(ram _ram){
		actualRam = _ram;
	}
	
	public int mbToBlock(int _mb) {
		if(_mb % 64 != 0){
			double number =  (_mb/ 64);
			_mb = (int) number;
			return _mb + 1;
		}
		else {
			double number =  (_mb/ 64);
			_mb = (int) number;
			return _mb;
		}
		
	}
	
	/**
	 * If the program is smaller than the ram, then it can be added to the ram. If the program is larger
	 * than the ram, and the ram is DDR, then the ram is increased. If the program is larger than the ram,
	 * and the ram is SDR, then the program cannot be added to the ram
	 * 
	 * @param _Program The program that is being added to the ram.
	 * @return The method is returning a boolean value.
	 */
	
	public boolean CanAddProgramToRam(ram _myRam, program _Program) {
		int ramSpace = _myRam.getAvailableMemory();
		int ProgramSpace = _Program.getProgramSize();
		String RamType = _myRam.getRamType();
		
		if (ramSpace > ProgramSpace) {
			return true;
		}
		
		else if (ramSpace <= ProgramSpace && RamType.equals("ddr")) {
			return true;
		}
		
		else if (ramSpace <= ProgramSpace && RamType.equals("sdr")) {
			return false;
		}
		return false;
	}
	
	
	/**
	 * This function adds a program to the ram
	 * 
	 * @param myRam the ram object
	 * @param _Program The program that is being added to the ram
	 */

	public void  addProgramToRam(ram myRam, program _Program) {
		
		myRam.getProgramsEXE().add(_Program);
		myRam.setAvailableMemory(myRam.getAvailableMemory() - _Program.getProgramSize());
	}
	
	/**
	 * This function adds a program to the queue of programs in the RAM
	 * 
	 * @param myRam the ram object
	 * @param _Program The program that is being added to the queue.
	 */

	public void  addProgramToQueue(ram myRam,program _Program) {
		myRam.getProgramsLINE().add(_Program);
	}
	
	/**
	 * This function takes in a ram object and Checks how much ram should be added so the program can run
	 * 
	 * @param _ram The ram object that is being modified.
	 */

	public void IncreaseRam(ram _ram) {
		int TotalMem = _ram.getRamStorage();
		int AvailableMem = _ram.getAvailableMemory();
		
		if(TotalMem < 256) {
			
			if (AvailableMem + 64 > 0) {
				_ram.setAvailableMemory(AvailableMem + 64);
			}
			
			else if (AvailableMem + 128 > 0) {
				_ram.setAvailableMemory(AvailableMem + 128);
			}
			else if (AvailableMem + 192 > 0) {
				_ram.setAvailableMemory(AvailableMem + 192);
			}
			
			else if (AvailableMem + 256 > 0) {
				_ram.setAvailableMemory(AvailableMem + 256);
			}
			
		}
		else if (TotalMem >= 256) {
			if (AvailableMem + 512 > 0) {
				_ram.setAvailableMemory(AvailableMem + 512);
			}
			if (AvailableMem + 1024 > 0) {
				_ram.setAvailableMemory(AvailableMem + 1024);
			}
		}
	}
	


	/**
	 * If the total memory is less than or equal to 256 and greater than 0, then decrease the total memory
	 * by 64. Otherwise, if the total memory is greater than 256 and greater than 0, then decrease the
	 * total memory by half
	 * 
	 * @param _ram The ram object that is being modified.
	 */

	public void DecreaseRam(ram _ram) {
		
		int TotalMem = _ram.getRamStorage();
		int AvailableMem = _ram.getAvailableMemory();
		
		if(TotalMem <= 256 && TotalMem > 0) {
			if (AvailableMem > TotalMem) {
				_ram.setAvailableMemory(AvailableMem - 64);
			}
		}
		
		else if(TotalMem > 256) {
			if (AvailableMem > TotalMem) {
				_ram.setAvailableMemory(AvailableMem / 2);
			}
		}
	}
	
	
	/**
	 * This function checks if a program has run out of time, if it has, it removes it from the ram
	 * 
	 * @param myRam is the ram object
	 * @return A boolean
	 */
	public boolean ProgramsTimeOutram (ram myRam) {
		for(int i = 0; i < myRam.getProgramsEXE().size(); i++) {
			if (myRam.getProgramsEXE().get(i).getRunTime() == 0) {
				myRam.setAvailableMemory(myRam.getAvailableMemory() + myRam.getProgramsEXE().get(i).getProgramSize());

				myRam.getProgramsEXE().remove(i);
				
				System.out.println("Se removio un programa");
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * This function reduces the run time of all the programs in the RAM by 1
	 * 
	 * @param myRam the ram object
	 */

	public void reduceProgramsTime(ram myRam) {
		for(int i = 0; i < myRam.getProgramsEXE().size(); i++) {
			myRam.getProgramsEXE().get(i).setRunTime(myRam.getProgramsEXE().get(i).getRunTime() - 1) ; 
		}
	}

}
;
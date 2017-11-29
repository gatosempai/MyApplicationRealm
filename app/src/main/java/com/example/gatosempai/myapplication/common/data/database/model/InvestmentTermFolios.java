package com.example.gatosempai.myapplication.common.data.database.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class InvestmentTermFolios extends RealmObject implements BaseModel {

	@PrimaryKey
	private long id;

	private String investmentId;
	private Double investmentBalance;
	private Double investmentPerformance;
	private Date investmentDueDate;
    private String sInvestmentDueDate;
	
	public InvestmentTermFolios(){
		this.setInvestmentId(null);
		this.setInvestmentBalance(null);
		this.setInvestmentPerformance(null);
		this.setInvestmentDueDate(null);
	}
	
	public InvestmentTermFolios(String investmentId, Double investmentBalance, Double investmentPerformance, Date investmentDueDate){
		this.setInvestmentId(investmentId);
		this.setInvestmentBalance(investmentBalance);
		this.setInvestmentPerformance(investmentPerformance);
		this.setInvestmentDueDate(investmentDueDate);
	}

	public InvestmentTermFolios(String investmentId, Double investmentBalance, Double investmentPerformance, String investmentDueDate){
		this.setInvestmentId(investmentId);
		this.setInvestmentBalance(investmentBalance);
		this.setInvestmentPerformance(investmentPerformance);
		this.setsInvestmentDueDate(investmentDueDate);
	}
	
	public InvestmentTermFolios(InvestmentTermFolios investmentTermFolios){
		this.setInvestmentId(investmentTermFolios.investmentId);
		this.setInvestmentBalance(investmentTermFolios.investmentBalance);
		this.setInvestmentPerformance(investmentTermFolios.investmentPerformance);
		this.setInvestmentDueDate(investmentTermFolios.investmentDueDate);
	}

	//Standard setters
	public void setInvestmentId(String investmentId) {
		this.investmentId = investmentId;
	}
	
	public void setInvestmentBalance(Double investmentBalance) {
		this.investmentBalance = investmentBalance;
	}
	
	public void setInvestmentPerformance(Double investmentPerformance) {
		this.investmentPerformance = investmentPerformance;
	}
	
	public void setInvestmentDueDate(Date investmentDueDate) {
		this.investmentDueDate = investmentDueDate;
	}

    public void setsInvestmentDueDate(String sInvestmentDueDate) {
        this.sInvestmentDueDate = sInvestmentDueDate;
    }

    //Standard getters
	public String getInvestmentId() {
		return investmentId;
	}
	
	public Double getInvestmentBalance() {
		return investmentBalance;
	}
	
	public Double getInvestmentPerformance() {
		return investmentPerformance;
	}
	
	public Date getInvestmentDueDate() {
		return investmentDueDate;
	}

    public String getsInvestmentDueDate() {
        return sInvestmentDueDate;
    }

    //Methods
	public String toString(){
		return "\t\ttinvestmentId:" + (this.investmentId != null ? this.investmentId:"") + "\n" +
			   "\t\tinvestmentBalance:" + (this.investmentBalance != null ? this.investmentBalance:"") + "\n" +
			   "\t\tinvestmentPerformance:" + (this.investmentPerformance != null ? this.investmentPerformance:"") + "\n" +
			   "\t\tinvestmentDueDate:" + (this.investmentDueDate != null ? this.investmentDueDate.toString():"") + "\n";
	}

	@Override
	public long getId() {
		return id;
	}
    public void setId(long id) {
        this.id = id;
    }
}

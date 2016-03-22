package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Consultation implements IAppElement{
	private String consID;
	private String patientSSN;
	private String diag;
	private List<String> meds;
	private LocalDate consultation_date;
	
	public Consultation() 
	{ 
		consID = "default_consID";
		patientSSN = "default_patientSSN";
		diag = "default_diag";
		meds = new ArrayList<>();
		consultation_date = LocalDate.now();
	};
	
	public Consultation (String consID, String PatientSSN, String diag, List<String> meds, LocalDate date)
	{
		this.consID = consID;
		this.patientSSN = PatientSSN;
		this.diag = diag;
		this.meds = meds;
		consultation_date = date;
	}
	
	public String getConsID() {
		return consID;
	}
	public void setConsID(String v_consID) {
		consID = v_consID;
	}
	
	public String getPatientSSN() {
		return patientSSN;
	}
	public void setPatientSSN(String patientSSN) {
		this.patientSSN = patientSSN;
	}
	public String getDiag() {
		return diag;
	}
	public void setDiag(String diag) {
		this.diag = diag;
	}
	public List<String> getMeds() {
		return meds;
	}
	public void setMeds(List<String> meds) {
		this.meds = meds;
	}
	public LocalDate getConsultation_date() {
		return consultation_date;
	}
	public void setConsultation_date(LocalDate date) {
		consultation_date = date;
	}

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(consID);
        sb.append(", ");
        sb.append(patientSSN);
        sb.append(", ");
        sb.append(diag);
        sb.append(", ");
        for(String med : meds){
            sb.append(med);
            sb.append(" + ");
        }
        sb.substring(0, sb.length()-2);
        sb.append(", ");
        sb.append(consultation_date.toString());
        return sb.toString();
	}

}

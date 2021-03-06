package com.breje.pm.persistance.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.breje.pm.exception.PatientsManagerException;
import com.breje.pm.model.Consultation;
import com.breje.pm.model.Entity;
import com.breje.pm.model.ObjectTypes;
import com.breje.pm.model.Patient;
import com.breje.pm.persistance.Repository;
import com.breje.pm.util.AppHelper;

@Deprecated
public class RepositoryImpl implements Repository {

	private String patients;
	private String consultations;

	public RepositoryImpl(String patients, String consultations) {
		this.patients = patients;
		this.consultations = consultations;
	}

	public void cleanFiles() throws PatientsManagerException {
		try {
			AppHelper.cleanFileContent(patients, consultations);
		} catch (IOException e) {
			throw new PatientsManagerException("IOException. Contact your administrator");
		}
	}

	public List<Patient> getPatients() throws PatientsManagerException {
		List<Patient> listOfPatients = new ArrayList<>();
		List<String> tokens = load(ObjectTypes.PATIENT);
		for (String token : tokens) {
			String[] patientString = token.split(",");
			Patient patient = new Patient(patientString[AppHelper.PATIENT_NAME_INDEX].trim(),
					patientString[AppHelper.PATIENT_SSN_INDEX].trim(),
					patientString[AppHelper.PATIENT_ADDRESS_INDEX].trim());
			listOfPatients.add(patient);
		}
		return listOfPatients;
	}

	private List<String> load(ObjectTypes type) throws PatientsManagerException {
		switch (type) {
		case PATIENT:
			return getObjectFromFile(patients);
		case CONSULTATION:
			return getObjectFromFile(consultations);
		default:
			throw new PatientsManagerException("Type of object error. Contact your administrator");
		}
	}

	private List<String> getObjectFromFile(String fileName) throws PatientsManagerException {
		List<String> listOfPatients = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(listOfPatients::add);
		} catch (IOException e) {
			throw new PatientsManagerException("Wrong reading object. Contact your administrator");
		}
		return listOfPatients;
	}

	public List<Consultation> getConsultations() throws PatientsManagerException {
		List<Consultation> lc = new ArrayList<>();
		List<String> tokens = load(ObjectTypes.CONSULTATION);
		for (String elem : tokens) {
			List<String> med = new ArrayList<>();
			String[] pat = elem.split(",");
			String[] meds = pat[3].split("\\+");
			for (String m : meds) {
				med.add(m.trim());
			}
			LocalDate date = LocalDate.parse(pat[4].trim(), AppHelper.DATE_FORMAT);
			Consultation c = new Consultation(pat[1].trim(), pat[2].trim(), med, date);
			lc.add(c);
		}
		return lc;
	}

	public void save(ObjectTypes type, Entity elem) throws PatientsManagerException {
		switch (type) {
		case PATIENT:
			saveObjectToFile(patients, elem);
			break;
		case CONSULTATION:
			saveObjectToFile(consultations, elem);
			break;
		default:
			throw new PatientsManagerException("Type of object error. Contact your administrator");
		}
	}

	private void saveObjectToFile(String fileName, Entity elem) throws PatientsManagerException {
		if (elem instanceof Patient) {
			Patient p = (Patient) elem;
			List<Patient> lp = getPatients();
			lp.add(p);
			List<String> lps = new ArrayList<>();
			lp.stream().map(String::valueOf).forEach(lps::add);
			writeObjectsIntoFile(fileName, lps);
		} else if (elem instanceof Consultation) {
			Consultation c = (Consultation) elem;
			List<Consultation> lc = getConsultations();
			lc.add(c);
			List<String> lcs = new ArrayList<>();
			lc.stream().map(String::valueOf).forEach(lcs::add);
			writeObjectsIntoFile(fileName, lcs);
		} else {
			throw new PatientsManagerException("Whatever. Contact your administrator.");
		}

	}

	private void writeObjectsIntoFile(String fileName, List<String> elems) throws PatientsManagerException {
		try {
			AppHelper.cleanFileContent(fileName);
			Files.write(Paths.get(fileName), elems);
		} catch (IOException e) {
			throw new PatientsManagerException("Whatever. Contact your administrator.");
		}
	}


	@Override
	public void save(Entity elem) throws PatientsManagerException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<?> getEntities() throws PatientsManagerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cleanFile() throws PatientsManagerException {
		// TODO Auto-generated method stub

	}

}

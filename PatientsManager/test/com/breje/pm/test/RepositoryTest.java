package com.breje.pm.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.breje.pm.exception.PatientsManagerException;
import com.breje.pm.model.ObjectTypes;
import com.breje.pm.model.Patient;
import com.breje.pm.persistance.impl.RepositoryImpl;

public class RepositoryTest {

	private Patient patient1 = null;
	private Patient patient2 = null;
	private static RepositoryImpl repository = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Test cases for Repository workflow had been started.");
		repository = new RepositoryImpl("persistance/pat-tests.txt", "persistance/cons-tests.txt");
		System.out.println("Repository of application has been initialized.");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		repository.cleanFiles();

		repository = null;
		System.out.println("Repository of application has been disposed.");
		System.out.println("Test cases for Repository workflow had been finished.");
	}

	@Before
	public void setUp() throws Exception {
		patient1 = new Patient("First User", "1990921314010", "acasa la el");
		patient2 = new Patient("Second User", "1890623336023", "acasa tot la el");
		System.out.println("Test case has been set up.");
	}

	@After
	public void tearDown() throws Exception {
		patient1 = null;
		patient2 = null;
		System.out.println("Test case has been finished.");
	}

	@Test
	public void testSave1() {
		try {
			repository.save(ObjectTypes.PATIENT, patient1);
		} catch (PatientsManagerException e) {
			e.printStackTrace();
		}
		List<Patient> patients = null;
		try {
			patients = repository.getPatients();
		} catch (PatientsManagerException e) {
			e.printStackTrace();
		}
		boolean existsPatient = false;
		for (Patient p : patients) {
			if (p.getSSN().equals(patient1.getSSN())) {
				existsPatient = true;
			}
		}
		assertTrue(existsPatient);
	}

	@Test
	public void testSave2() {
		try {
			repository.save(ObjectTypes.PATIENT, patient2);
		} catch (PatientsManagerException e) {
			e.printStackTrace();
		}
		List<Patient> patients = null;
		try {
			patients = repository.getPatients();
		} catch (PatientsManagerException e) {
			e.printStackTrace();
		}
		boolean existsPatient = false;
		for (Patient p : patients) {
			if (p.getSSN().equals(patient2.getSSN())) {
				existsPatient = true;
			}
		}
		assertTrue(existsPatient);
	}

	@Test
	public void testSave3() {
		patient1 = null;
		boolean isPatientNull = false;
		try {
			repository.save(ObjectTypes.PATIENT, patient1);
		} catch (PatientsManagerException e) {
			isPatientNull = true;
		}
		assertTrue(isPatientNull);
	}

}

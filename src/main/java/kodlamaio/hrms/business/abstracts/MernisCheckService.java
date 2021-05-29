package kodlamaio.hrms.business.abstracts;

import java.time.LocalDate;

import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.Candidate;

public interface MernisCheckService {
	Result checkIfRealPerson(Candidate candidate);
	Result checkVirtualPerson(String nationalityId, String firstName, String lastName, LocalDate birthday);
}

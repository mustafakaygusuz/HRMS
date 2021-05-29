package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.business.abstracts.MernisCheckService;
import kodlamaio.hrms.core.utilities.adapters.MernisAdapterService;
import kodlamaio.hrms.core.utilities.business.BusinessRules;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.entities.concretes.Candidate;

@Service
public class CandidateManager implements CandidateService {

	private CandidateDao candidateDao;
	private MernisCheckService checkCandidate;

	@Autowired
	public CandidateManager(CandidateDao candidateDao) {
		super();
		this.candidateDao = candidateDao;
		this.checkCandidate = new MernisAdapterService();
	}

	@Override
	public DataResult<List<Candidate>> getAll() {
		return new SuccessDataResult<List<Candidate>>(this.candidateDao.findAll(), "Kullanıcılar listelendi");
	}

	@Override
	public Result add(Candidate candidate) {

	
		Result result = BusinessRules.run(
										isMailExist(candidate.getEmail()),
										isNationalityIdExist(candidate.getIdentificationNumber()),
										mernisControl(candidate));
		if (result != null) {

			return result;
		} else {
			this.candidateDao.save(candidate);
			return new SuccessResult("Kullanıcı eklendi.");
		}

	}

	private Result isMailExist(String mail) {

		if (candidateDao.findByEmailContaining(mail).isEmpty()) {
			return new SuccessResult();

		} else {
			return new ErrorResult("Bu mail ile kayıtlı kullanıcı var.");
		}

	}
	
	private Result isNationalityIdExist(String nationalityId) {

		if (candidateDao.findByIdentificationNumberContaining(nationalityId).isEmpty()) {
			return new SuccessResult();

		} else {
			return new ErrorResult("Bu Tc kimlik no ile kayıtlı kullanıcı var.");
		}
	}
	
	private Result mernisControl(Candidate candidate) {
		
		if (this.checkCandidate.checkVirtualPerson(candidate.getIdentificationNumber(), 
												candidate.getFirstName(),
												candidate.getLastName(),
												candidate.getBirthDate()).isSuccess()) {
			return new SuccessResult();
		}
		return new ErrorResult("Doğrulama başarısız");
	}

}

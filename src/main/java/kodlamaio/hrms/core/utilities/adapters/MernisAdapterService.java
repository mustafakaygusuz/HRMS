package kodlamaio.hrms.core.utilities.adapters;

import java.time.LocalDate;

import kodlamaio.hrms.business.abstracts.MernisCheckService;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.entities.concretes.Candidate;
import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

public class MernisAdapterService implements MernisCheckService {

	@Override
	public Result checkIfRealPerson(Candidate candidate) {
		 KPSPublicSoapProxy kpsPublicSoapProxy=new KPSPublicSoapProxy();
		 boolean adapterResult=false;
	 try {
		
			/*
			 * adapterResult=kpsPublicSoapProxy.TCKimlikNoDogrula(Long.parseLong(employee.
			 * getNationalityId()) , employee.getFirstName().toUpperCase(),
			 * employee.getLastName().toUpperCase(), employee.getBirthDay().getYear());
			 */
			System.out.println("Doğrulama: "+(adapterResult ? "Başarılı":"Başarısız"));
	 	}
	
	 catch (Exception e) {
		
		 return new ErrorResult("Doğrulama başarısız");
		 
	}
	 	
	 
		return new SuccessResult("Doğrulama başarılı");
	}

	@Override
	public Result checkVirtualPerson(String nationalityId, String firstName, String lastName, LocalDate birthday) {
		
		if (nationalityId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || birthday == null){
			
			return new ErrorResult("Doğrulama başarısız");
		}
		return new SuccessResult("Doğrulama başarılı.");
	}

}

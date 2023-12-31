package com.ccsw.tutorial.lending;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.lending.model.Lending;
import com.ccsw.tutorial.lending.model.LendingDto;
import com.ccsw.tutorial.lending.model.LendingResponseDto;
import com.ccsw.tutorial.lending.model.LendingSearchDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LendingServiceImpl implements LendingService {
    @Autowired
    LendingRepository lendingRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    GameService gameService;

    @Override
    public Page<Lending> findPage(LendingSearchDto dto) {

        LendingSpecification dateLow = new LendingSpecification(
                new SearchCriteria("dateend", ">=", dto.getLendingFilter().getDate()));

        LendingSpecification dateHigh = new LendingSpecification(
                new SearchCriteria("dateinit", "<=", dto.getLendingFilter().getDate()));

        LendingSpecification client = new LendingSpecification(
                new SearchCriteria("client.id", ":", dto.getLendingFilter().getClientId()));

        LendingSpecification title = new LendingSpecification(
                new SearchCriteria("game.id", ":", dto.getLendingFilter().getTitleId()));

        Specification<Lending> spec = Specification.where(dateLow).and(dateHigh).and(client).and(title);
        return this.lendingRepository.findAll(spec, dto.getPageable().getPageable());
    }

    @Override
    public void delete(Long id) {
        lendingRepository.deleteById(id);

    }

    @Override
    public List<Lending> findAll() {
        return (List<Lending>) lendingRepository.findAll();
    }

    @Override
    public LendingResponseDto save(LendingDto dto) {
        LendingSpecification dateEndBtw = new LendingSpecification(new SearchCriteria("dateend", "<>", dto));

        LendingSpecification dateInitBtw = new LendingSpecification(new SearchCriteria("dateinit", "<>", dto));

        LendingSpecification client = new LendingSpecification(
                new SearchCriteria("client.id", ":", dto.getClient().getId()));

        LendingSpecification title = new LendingSpecification(
                new SearchCriteria("game.id", ":", dto.getGame().getId()));

        // Si existe una reserva para ese juego entre las fechas
        Specification<Lending> specG = Specification.where(dateInitBtw).or(dateEndBtw).and(title);
        LendingResponseDto response = new LendingResponseDto();

        List<Lending> resultsG = this.lendingRepository.findAll(specG);
        if (resultsG.size() > 0) {
            response.setError("Juego esta ocupado entre: ".concat(formatDate(resultsG.get(0).getDateinit()))
                    .concat(" y ").concat(formatDate(resultsG.get(0).getDateend())));
            return response;

        }

        // Si existe una reserva para ese cliente entre las fechas
        Specification<Lending> specC = Specification.where(dateInitBtw).or(dateEndBtw).and(client);

        List<Lending> resultsC = this.lendingRepository.findAll(specC);
        if (resultsC.size() > 1) {
            response.setError("Cliente ya tiene reserva:".concat(formatReturn(resultsC)));
            return response;
        }

        // Si la fecha es superior a 14 días no debe de registrarse
        int milToDays = 1000 * 60 * 60 * 24;
        if ((dto.getDateend().getTime() - dto.getDateinit().getTime()) / milToDays >= 14) {
            System.out.println((dto.getDateend().getTime() - dto.getDateinit().getTime()) / milToDays);
            response.setError("La reserva no puedde ser superior a 14 días.");
            return response;
        }

        Lending lending = new Lending();
        BeanUtils.copyProperties(dto, lending, "id");

        lending.setClient(clientService.get(dto.getClient().getId()));
        lending.setGame(gameService.get(dto.getGame().getId()));

        this.lendingRepository.save(lending);

        return null;
    }

    private String formatDate(Date date) {
        System.out.println(date);
        String formater = "";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        formater = dateFormat.format(date);
        return formater;
    }

    private String formatReturn(List<Lending> results) {
        String response = " ";
        for (Lending e : results) {
            response += e.getGame().getTitle() + " " + formatDate(e.getDateinit()) + " y " + formatDate(e.getDateend())
                    + " ";
        }
        return response;
    }

}

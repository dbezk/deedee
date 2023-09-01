package ua.project.deedee.service;

import ua.project.deedee.dto.rating.RatingDto;

import java.util.List;

public interface IUserStatisticService {

    public List<RatingDto> getRatingList();

}

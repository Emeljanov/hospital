package by.emel.anton.model.dao.implementation.hibernatedao;

import by.emel.anton.model.beans.therapy.OrdinaryTherapy;
import by.emel.anton.model.beans.therapy.Therapy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class HibRepo implements JpaRepository<OrdinaryTherapy, Integer> {
    @Override
    public List<OrdinaryTherapy> findAll() {
        return null;
    }

    @Override
    public List<OrdinaryTherapy> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<OrdinaryTherapy> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<OrdinaryTherapy> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(OrdinaryTherapy ordinaryTherapy) {

    }

    @Override
    public void deleteAll(Iterable<? extends OrdinaryTherapy> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends OrdinaryTherapy> S save(S s) {
        return null;
    }

    @Override
    public <S extends OrdinaryTherapy> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<OrdinaryTherapy> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends OrdinaryTherapy> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<OrdinaryTherapy> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public OrdinaryTherapy getOne(Integer integer) {
        return null;
    }

    @Override
    public <S extends OrdinaryTherapy> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends OrdinaryTherapy> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends OrdinaryTherapy> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends OrdinaryTherapy> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends OrdinaryTherapy> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends OrdinaryTherapy> boolean exists(Example<S> example) {
        return false;
    }
}

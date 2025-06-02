package com.esteticaAutomotiva.domain.agendamento.enums;

import java.time.LocalTime;

public enum HorarioConsulta {

    HORARIO_08_00(LocalTime.of(8, 0)),
    HORARIO_09_00(LocalTime.of(9, 0)),
    HORARIO_10_00(LocalTime.of(10, 0)),
    HORARIO_11_00(LocalTime.of(11, 0)),
    HORARIO_13_00(LocalTime.of(13, 0)),
    HORARIO_14_00(LocalTime.of(14, 0)),
    HORARIO_15_00(LocalTime.of(15, 0)),
    HORARIO_16_00(LocalTime.of(16, 0)),
    HORARIO_17_00(LocalTime.of(17, 0));

    private final LocalTime horario;

    HorarioConsulta(LocalTime horario) {
        this.horario = horario;
    }

    public LocalTime getHorario() {
        return horario;
    }

    // metodo para verificacao do horario valido
    public static boolean isHorarioValido(LocalTime localTime) {
        for (HorarioConsulta h : HorarioConsulta.values()) {
            if (h.getHorario().equals(localTime)) {
                return true;
            }
        }
        return false;
    }
}

./test.sh
fgrep -ve "#" actividades_solapadas.log | sort | java FSM 


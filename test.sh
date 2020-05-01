LOGSIMPLE="actividades_un_evento.log"
LOGDOBLE="actividades_dos_eventos.log"
LOGSOLAPADO="actividades_solapadas.log"

SIMPLE="SimpleFSM"
MULTIPLE="FSM"

ATTACKPATTERN="ATTACK detectado"

testNoAttackSimple() {
  assertEquals 0 $( grep -ve "#" "$LOGSIMPLE" | grep "ruido_ok" | java "$SIMPLE" | grep "$ATTACKPATTERN" | wc -l )
}

testSimpleUn()
{
  assertEquals 1 $( grep -ve "#" "$LOGSIMPLE" | sort | java "$SIMPLE" | grep "$ATTACKPATTERN" | wc -l )
}

testSimpleDos()
{
  assertEquals 2 $( grep -ve "#" "$LOGDOBLE" | sort | java "$SIMPLE" | grep "$ATTACKPATTERN" | wc -l )
}

testMultipleNoAttack() {
  assertEquals 0 $( grep -ve "#" "$LOGSIMPLE" | grep "ruido_ok" | java "$MULTIPLE" | grep "$ATTACKPATTERN" | wc -l )
}

testMultipleUn()
{
  assertEquals 1 $( grep -ve "#" "$LOGSIMPLE" | sort | java "$MULTIPLE" | grep "$ATTACKPATTERN" | wc -l )
}

testMultipleDos()
{
  assertEquals 2 $( grep -ve "#" "$LOGDOBLE" | sort | java "$MULTIPLE" | grep "$ATTACKPATTERN" | wc -l )
}

testMultipleSolapadas()
{
  assertEquals 2 $( grep -ve "#" "$LOGSOLAPADO" | sort | java "$MULTIPLE" | grep "$ATTACKPATTERN" | wc -l )
}




. shunit2

package jt.flights.search.data

import junit.framework.TestCase.assertTrue
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class FlightResultParsing {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Test
    fun `Individual Flight can be decoded from Json`() {
        val data = """
            {"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":null,"actual_runway_on":null,"fa_flight_id":"SIA321-1708639911-schedule-14p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":null,"atc_ident":null,"inbound_fa_flight_id":null,"codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":true,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":0,"arrival_delay":0,"filed_ete":45300,"foresight_predictions_available":false,"scheduled_out":"2024-02-24T22:05:00Z","estimated_out":null,"actual_out":null,"scheduled_off":"2024-02-24T22:15:00Z","estimated_off":"2024-02-24T22:15:00Z","actual_off":null,"scheduled_on":"2024-02-25T10:50:00Z","estimated_on":"2024-02-25T10:50:00Z","actual_on":null,"scheduled_in":"2024-02-25T11:00:00Z","estimated_in":null,"actual_in":null,"progress_percent":0,"status":"Scheduled","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":null,"seats_cabin_business":48,"seats_cabin_coach":212,"seats_cabin_first":4,"gate_origin":null,"gate_destination":null,"terminal_origin":null,"terminal_destination":null,"type":"Airline"}
        """.trimIndent()
        val flight = json.decodeFromString<FlightAware.Flight>(data)
        assertEquals("SIA321", flight.ident)
        assertEquals("B77W", flight.aircraftType)
        assertEquals("SIA", flight.operator)
        assertEquals("SIA", flight.operatorIcao)
        assertFalse(flight.blocked)
        assertFalse(flight.cancelled)
        assertTrue(flight.diverted)
    }

    @Test
    fun `A flight result can be decoded from Json`() {
        val data = """
            {"flights":[{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":null,"actual_runway_on":null,"fa_flight_id":"SIA321-1708639911-schedule-14p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":null,"atc_ident":null,"inbound_fa_flight_id":null,"codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":0,"arrival_delay":0,"filed_ete":45300,"foresight_predictions_available":false,"scheduled_out":"2024-02-24T22:05:00Z","estimated_out":null,"actual_out":null,"scheduled_off":"2024-02-24T22:15:00Z","estimated_off":"2024-02-24T22:15:00Z","actual_off":null,"scheduled_on":"2024-02-25T10:50:00Z","estimated_on":"2024-02-25T10:50:00Z","actual_on":null,"scheduled_in":"2024-02-25T11:00:00Z","estimated_in":null,"actual_in":null,"progress_percent":0,"status":"Scheduled","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":null,"seats_cabin_business":48,"seats_cabin_coach":212,"seats_cabin_first":4,"gate_origin":null,"gate_destination":null,"terminal_origin":null,"terminal_destination":null,"type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":null,"actual_runway_on":null,"fa_flight_id":"SIA321-1708553917-schedule-1675p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":null,"atc_ident":null,"inbound_fa_flight_id":null,"codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":0,"arrival_delay":0,"filed_ete":45300,"foresight_predictions_available":false,"scheduled_out":"2024-02-23T22:05:00Z","estimated_out":null,"actual_out":null,"scheduled_off":"2024-02-23T22:15:00Z","estimated_off":"2024-02-23T22:15:00Z","actual_off":null,"scheduled_on":"2024-02-24T10:50:00Z","estimated_on":"2024-02-24T10:50:00Z","actual_on":null,"scheduled_in":"2024-02-24T11:00:00Z","estimated_in":null,"actual_in":null,"progress_percent":0,"status":"Scheduled","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":null,"seats_cabin_business":48,"seats_cabin_coach":212,"seats_cabin_first":4,"gate_origin":null,"gate_destination":null,"terminal_origin":null,"terminal_destination":null,"type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":"27L","actual_runway_on":null,"fa_flight_id":"SIA321-1708467665-schedule-9p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":"9V-SWP","atc_ident":null,"inbound_fa_flight_id":"SIA318-1708405470-schedule-789p","codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":798,"arrival_delay":600,"filed_ete":46320,"foresight_predictions_available":true,"scheduled_out":"2024-02-22T22:05:00Z","estimated_out":"2024-02-22T21:58:00Z","actual_out":"2024-02-22T22:18:18Z","scheduled_off":"2024-02-22T22:15:00Z","estimated_off":"2024-02-22T22:18:18Z","actual_off":"2024-02-22T22:18:18Z","scheduled_on":"2024-02-23T11:07:00Z","estimated_on":"2024-02-23T11:00:00Z","actual_on":null,"scheduled_in":"2024-02-23T11:00:00Z","estimated_in":"2024-02-23T11:10:00Z","actual_in":null,"progress_percent":1,"status":"On The Way! / On Time","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":null,"seats_cabin_business":null,"seats_cabin_coach":null,"seats_cabin_first":null,"gate_origin":"B39","gate_destination":null,"terminal_origin":"2","terminal_destination":null,"type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":"27L","actual_runway_on":"02L","fa_flight_id":"SIA321-1708380630-schedule-873p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":"9V-SWL","atc_ident":null,"inbound_fa_flight_id":"SIA318-1708318454-schedule-548p","codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":224,"arrival_delay":-4183,"filed_ete":45900,"foresight_predictions_available":true,"scheduled_out":"2024-02-21T22:05:00Z","estimated_out":"2024-02-21T21:51:00Z","actual_out":"2024-02-21T22:08:44Z","scheduled_off":"2024-02-21T22:15:00Z","estimated_off":"2024-02-21T22:08:44Z","actual_off":"2024-02-21T22:08:44Z","scheduled_on":"2024-02-22T11:00:00Z","estimated_on":"2024-02-22T09:50:17Z","actual_on":"2024-02-22T09:50:17Z","scheduled_in":"2024-02-22T11:00:00Z","estimated_in":"2024-02-22T10:00:09Z","actual_in":"2024-02-22T09:50:17Z","progress_percent":100,"status":"Arrived / Gate Arrival","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":"38","seats_cabin_business":null,"seats_cabin_coach":null,"seats_cabin_first":null,"gate_origin":"B38","gate_destination":null,"terminal_origin":"2","terminal_destination":"2","type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":"27L","actual_runway_on":"02L","fa_flight_id":"SIA321-1708294671-schedule-3017p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":"9V-SWZ","atc_ident":null,"inbound_fa_flight_id":"SIA318-1708235182-schedule-272p","codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":723,"arrival_delay":-1956,"filed_ete":45900,"foresight_predictions_available":true,"scheduled_out":"2024-02-20T22:05:00Z","estimated_out":"2024-02-20T22:00:00Z","actual_out":"2024-02-20T22:17:03Z","scheduled_off":"2024-02-20T22:15:00Z","estimated_off":"2024-02-20T22:17:03Z","actual_off":"2024-02-20T22:17:03Z","scheduled_on":"2024-02-21T11:00:00Z","estimated_on":"2024-02-21T10:27:24Z","actual_on":"2024-02-21T10:27:24Z","scheduled_in":"2024-02-21T11:00:00Z","estimated_in":"2024-02-21T10:37:13Z","actual_in":"2024-02-21T10:27:24Z","progress_percent":100,"status":"Arrived / Gate Arrival","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":"38","seats_cabin_business":null,"seats_cabin_coach":null,"seats_cabin_first":null,"gate_origin":"B48","gate_destination":null,"terminal_origin":"2","terminal_destination":"2","type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":"27L","actual_runway_on":"02L","fa_flight_id":"SIA321-1708208306-schedule-71p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":"9V-SWK","atc_ident":null,"inbound_fa_flight_id":"SIA318-1708145924-schedule-140p","codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":1935,"arrival_delay":-819,"filed_ete":45060,"foresight_predictions_available":true,"scheduled_out":"2024-02-19T22:05:00Z","estimated_out":"2024-02-19T22:19:00Z","actual_out":"2024-02-19T22:37:15Z","scheduled_off":"2024-02-19T22:15:00Z","estimated_off":"2024-02-19T22:37:15Z","actual_off":"2024-02-19T22:37:15Z","scheduled_on":"2024-02-20T10:46:00Z","estimated_on":"2024-02-20T10:46:21Z","actual_on":"2024-02-20T10:46:21Z","scheduled_in":"2024-02-20T11:00:00Z","estimated_in":"2024-02-20T10:56:21Z","actual_in":"2024-02-20T10:46:21Z","progress_percent":100,"status":"Arrived / Gate Arrival","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":"20","seats_cabin_business":null,"seats_cabin_coach":null,"seats_cabin_first":null,"gate_origin":"B43","gate_destination":null,"terminal_origin":"2","terminal_destination":"1","type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":"27R","actual_runway_on":"02L","fa_flight_id":"SIA321-1708121176-schedule-108p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":"9V-SWZ","atc_ident":null,"inbound_fa_flight_id":"SIA318-1708058735-schedule-245p","codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":1550,"arrival_delay":-1838,"filed_ete":45900,"foresight_predictions_available":true,"scheduled_out":"2024-02-18T22:05:00Z","estimated_out":"2024-02-18T22:05:00Z","actual_out":"2024-02-18T22:30:50Z","scheduled_off":"2024-02-18T22:15:00Z","estimated_off":"2024-02-18T22:30:50Z","actual_off":"2024-02-18T22:30:50Z","scheduled_on":"2024-02-19T11:00:00Z","estimated_on":"2024-02-19T10:29:22Z","actual_on":"2024-02-19T10:29:22Z","scheduled_in":"2024-02-19T11:00:00Z","estimated_in":"2024-02-19T10:39:03Z","actual_in":"2024-02-19T10:29:22Z","progress_percent":100,"status":"Arrived / Gate Arrival","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":"47","seats_cabin_business":null,"seats_cabin_coach":null,"seats_cabin_first":null,"gate_origin":"B38","gate_destination":null,"terminal_origin":"2","terminal_destination":"3","type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":"27R","actual_runway_on":"02L","fa_flight_id":"SIA321-1708034893-schedule-366p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":"9V-SWY","atc_ident":null,"inbound_fa_flight_id":"SIA318-1707972373-schedule-1323p","codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":314,"arrival_delay":-2460,"filed_ete":46560,"foresight_predictions_available":true,"scheduled_out":"2024-02-17T22:05:00Z","estimated_out":"2024-02-17T21:54:00Z","actual_out":"2024-02-17T22:10:14Z","scheduled_off":"2024-02-17T22:15:00Z","estimated_off":"2024-02-17T22:10:14Z","actual_off":"2024-02-17T22:10:14Z","scheduled_on":"2024-02-18T11:11:00Z","estimated_on":"2024-02-18T10:19:00Z","actual_on":"2024-02-18T10:19:00Z","scheduled_in":"2024-02-18T11:00:00Z","estimated_in":"2024-02-18T10:29:00Z","actual_in":"2024-02-18T10:19:00Z","progress_percent":100,"status":"Arrived / Gate Arrival","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":"47","seats_cabin_business":null,"seats_cabin_coach":null,"seats_cabin_first":null,"gate_origin":"B38","gate_destination":null,"terminal_origin":"2","terminal_destination":"3","type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":"27R","actual_runway_on":"02L","fa_flight_id":"SIA321-1707948647-schedule-296p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":"9V-SNB","atc_ident":null,"inbound_fa_flight_id":"SIA318-1707886450-schedule-2p","codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":1051,"arrival_delay":-1761,"filed_ete":45900,"foresight_predictions_available":true,"scheduled_out":"2024-02-16T22:05:00Z","estimated_out":"2024-02-16T22:02:00Z","actual_out":"2024-02-16T22:22:31Z","scheduled_off":"2024-02-16T22:15:00Z","estimated_off":"2024-02-16T22:22:31Z","actual_off":"2024-02-16T22:22:31Z","scheduled_on":"2024-02-17T11:00:00Z","estimated_on":"2024-02-17T10:30:39Z","actual_on":"2024-02-17T10:30:39Z","scheduled_in":"2024-02-17T11:00:00Z","estimated_in":"2024-02-17T10:40:29Z","actual_in":"2024-02-17T10:30:39Z","progress_percent":100,"status":"Arrived / Gate Arrival","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":"32","seats_cabin_business":null,"seats_cabin_coach":null,"seats_cabin_first":null,"gate_origin":"B38","gate_destination":null,"terminal_origin":"2","terminal_destination":"2","type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":"27R","actual_runway_on":"02L","fa_flight_id":"SIA321-1707862746-schedule-2036p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":"9V-SWI","atc_ident":null,"inbound_fa_flight_id":"SIA318-1707800608-schedule-288p","codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":117,"arrival_delay":-100,"filed_ete":45900,"foresight_predictions_available":true,"scheduled_out":"2024-02-15T22:05:00Z","estimated_out":"2024-02-15T21:51:00Z","actual_out":"2024-02-15T22:06:57Z","scheduled_off":"2024-02-15T22:15:00Z","estimated_off":"2024-02-15T22:06:57Z","actual_off":"2024-02-15T22:06:57Z","scheduled_on":"2024-02-16T11:00:00Z","estimated_on":"2024-02-16T10:58:20Z","actual_on":"2024-02-16T10:58:20Z","scheduled_in":"2024-02-16T11:00:00Z","estimated_in":"2024-02-16T11:08:20Z","actual_in":"2024-02-16T10:58:20Z","progress_percent":100,"status":"Arrived / Gate Arrival","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":"31","seats_cabin_business":null,"seats_cabin_coach":null,"seats_cabin_first":null,"gate_origin":"B38","gate_destination":null,"terminal_origin":"2","terminal_destination":"2","type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":"27R","actual_runway_on":"02L","fa_flight_id":"SIA321-1707776046-schedule-1544p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":"9V-SWU","atc_ident":null,"inbound_fa_flight_id":"SIA318-1707714138-schedule-764p","codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":631,"arrival_delay":-1274,"filed_ete":46380,"foresight_predictions_available":true,"scheduled_out":"2024-02-14T22:05:00Z","estimated_out":"2024-02-14T21:57:00Z","actual_out":"2024-02-14T22:15:31Z","scheduled_off":"2024-02-14T22:15:00Z","estimated_off":"2024-02-14T22:15:31Z","actual_off":"2024-02-14T22:15:31Z","scheduled_on":"2024-02-15T11:08:00Z","estimated_on":"2024-02-15T10:38:46Z","actual_on":"2024-02-15T10:38:46Z","scheduled_in":"2024-02-15T11:00:00Z","estimated_in":"2024-02-15T10:48:46Z","actual_in":"2024-02-15T10:38:46Z","progress_percent":100,"status":"Arrived / Gate Arrival","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":"30","seats_cabin_business":null,"seats_cabin_coach":null,"seats_cabin_first":null,"gate_origin":"B33","gate_destination":null,"terminal_origin":"2","terminal_destination":"2","type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":"27R","actual_runway_on":"02L","fa_flight_id":"SIA321-1707689637-schedule-282p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":"9V-SWO","atc_ident":null,"inbound_fa_flight_id":"SIA318-1707627560-schedule-302p","codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":-177,"arrival_delay":-1339,"filed_ete":47100,"foresight_predictions_available":true,"scheduled_out":"2024-02-13T22:05:00Z","estimated_out":"2024-02-13T21:45:00Z","actual_out":"2024-02-13T22:02:03Z","scheduled_off":"2024-02-13T22:15:00Z","estimated_off":"2024-02-13T22:02:03Z","actual_off":"2024-02-13T22:02:03Z","scheduled_on":"2024-02-14T11:20:00Z","estimated_on":"2024-02-14T10:37:41Z","actual_on":"2024-02-14T10:37:41Z","scheduled_in":"2024-02-14T11:00:00Z","estimated_in":"2024-02-14T10:47:23Z","actual_in":"2024-02-14T10:37:41Z","progress_percent":100,"status":"Arrived / Gate Arrival","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":"46","seats_cabin_business":null,"seats_cabin_coach":null,"seats_cabin_first":null,"gate_origin":"B38","gate_destination":null,"terminal_origin":"2","terminal_destination":"3","type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":"27R","actual_runway_on":"20C","fa_flight_id":"SIA321-1707603089-schedule-749p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":"9V-SWG","atc_ident":null,"inbound_fa_flight_id":"SIA318-1707541304-schedule-139p","codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":1082,"arrival_delay":1212,"filed_ete":46020,"foresight_predictions_available":true,"scheduled_out":"2024-02-12T22:05:00Z","estimated_out":"2024-02-12T22:03:00Z","actual_out":"2024-02-12T22:23:02Z","scheduled_off":"2024-02-12T22:15:00Z","estimated_off":"2024-02-12T22:23:02Z","actual_off":"2024-02-12T22:23:02Z","scheduled_on":"2024-02-13T11:02:00Z","estimated_on":"2024-02-13T11:20:12Z","actual_on":"2024-02-13T11:20:12Z","scheduled_in":"2024-02-13T11:00:00Z","estimated_in":"2024-02-13T11:30:01Z","actual_in":"2024-02-13T11:20:12Z","progress_percent":100,"status":"Arrived / Gate Arrival","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":"38","seats_cabin_business":null,"seats_cabin_coach":null,"seats_cabin_first":null,"gate_origin":"B38","gate_destination":null,"terminal_origin":"2","terminal_destination":"2","type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":"27L","actual_runway_on":"02C","fa_flight_id":"SIA321-1707516897-schedule-1985p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":"9V-SWQ","atc_ident":null,"inbound_fa_flight_id":"SIA318-1707455027-schedule-609p","codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":575,"arrival_delay":-556,"filed_ete":46260,"foresight_predictions_available":true,"scheduled_out":"2024-02-11T22:05:00Z","estimated_out":"2024-02-11T21:59:00Z","actual_out":"2024-02-11T22:14:35Z","scheduled_off":"2024-02-11T22:15:00Z","estimated_off":"2024-02-11T22:14:35Z","actual_off":"2024-02-11T22:14:35Z","scheduled_on":"2024-02-12T11:06:00Z","estimated_on":"2024-02-12T10:50:44Z","actual_on":"2024-02-12T10:50:44Z","scheduled_in":"2024-02-12T11:00:00Z","estimated_in":"2024-02-12T11:01:00Z","actual_in":"2024-02-12T10:50:44Z","progress_percent":100,"status":"Arrived / Gate Arrival","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":"18","seats_cabin_business":null,"seats_cabin_coach":null,"seats_cabin_first":null,"gate_origin":"B38","gate_destination":null,"terminal_origin":"2","terminal_destination":"1","type":"Airline"},{"ident":"SIA321","ident_icao":"SIA321","ident_iata":"SQ321","actual_runway_off":"27L","actual_runway_on":"02L","fa_flight_id":"SIA321-1707430538-schedule-634p","operator":"SIA","operator_icao":"SIA","operator_iata":"SQ","flight_number":"321","registration":"9V-SWP","atc_ident":null,"inbound_fa_flight_id":null,"codeshares":["ANZ3321","AVA6550","SAS8041","VIR7968","TAP8405","VOZ5567","MAS5765","FJI5902"],"codeshares_iata":["NZ3321","AV6550","SK8041","VS7968","TP8405","VA5567","MH5765","FJ5902"],"blocked":false,"diverted":false,"cancelled":false,"position_only":false,"origin":{"code":"EGLL","code_icao":"EGLL","code_iata":"LHR","code_lid":null,"timezone":"Europe/London","name":"London Heathrow","city":"London","airport_info_url":"/airports/EGLL"},"destination":{"code":"WSSS","code_icao":"WSSS","code_iata":"SIN","code_lid":null,"timezone":"Asia/Singapore","name":"Singapore Changi","city":"Singapore","airport_info_url":"/airports/WSSS"},"departure_delay":1334,"arrival_delay":-899,"filed_ete":45840,"foresight_predictions_available":true,"scheduled_out":"2024-02-10T22:05:00Z","estimated_out":"2024-02-10T22:06:00Z","actual_out":"2024-02-10T22:27:14Z","scheduled_off":"2024-02-10T22:15:00Z","estimated_off":"2024-02-10T22:27:14Z","actual_off":"2024-02-10T22:27:14Z","scheduled_on":"2024-02-11T10:59:00Z","estimated_on":"2024-02-11T10:45:01Z","actual_on":"2024-02-11T10:45:01Z","scheduled_in":"2024-02-11T11:00:00Z","estimated_in":"2024-02-11T10:54:49Z","actual_in":"2024-02-11T10:45:01Z","progress_percent":100,"status":"Arrived / Gate Arrival","aircraft_type":"B77W","route_distance":6769,"filed_airspeed":468,"filed_altitude":null,"route":null,"baggage_claim":"46","seats_cabin_business":null,"seats_cabin_coach":null,"seats_cabin_first":null,"gate_origin":"B38","gate_destination":null,"terminal_origin":"2","terminal_destination":"3","type":"Airline"}],"links":null,"num_pages":1}
        """.trimIndent()
        val flightsResult = json.decodeFromString<FlightAware.FlightsResult>(data)
        assertNotNull(flightsResult.flights.firstOrNull())
        assertEquals(15, flightsResult.flights.size)
    }
}
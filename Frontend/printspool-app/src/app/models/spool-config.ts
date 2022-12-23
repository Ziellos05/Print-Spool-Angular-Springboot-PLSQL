// Model para generar el objeto que se envía como petición a la API y generar el Print Spool

export class SpoolConfig {
    date: string
    stratum: boolean
    avgConsumption: boolean
    lastConsumption: boolean
    nconsumptions: number
}

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-zakimadaoui.git;protocol=ssh;branch=main"
SRC_URI += "file://misc-modules-start-stop"
SRC_URI += "file://module_load"
SRC_URI += "file://module_unload"

PV = "1.0+git${SRCPV}"
SRCREV = "cedfe6aa79955202a73e2786766bc504cc298c78"

S = "${WORKDIR}/git/misc-modules"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

# Inherit statup script utilities class and provide necessary config
inherit update-rc.d
# Flag this package as one that uses init WORKDIR
INITSCRIPT_PACKAGES="${PN}"
INITSCRIPT_NAME:${PN}= "misc-modules-start-stop" 

do_install:append() {
    install -d ${D}${INIT_D_DIR}
    install -m 0755 ${WORKDIR}/misc-modules-start-stop   ${D}${INIT_D_DIR}/misc-modules-start-stop
	install -m 0755 ${WORKDIR}/module_load         ${D}${INIT_D_DIR}/module_load
	install -m 0755 ${WORKDIR}/module_unload       ${D}${INIT_D_DIR}/module_unload
}

FILES:${PN} += "\
	${INIT_D_DIR}/misc-modules-start-stop \
	${INIT_D_DIR}/module_load \
	${INIT_D_DIR}/module_unload \
"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-zakimadaoui.git;protocol=ssh;branch=main"
SRC_URI += "file://scull_load"
SRC_URI += "file://scull_unload"
SRC_URI += "file://scull-start-stop"

PV = "1.0+git${SRCPV}"
SRCREV = "cedfe6aa79955202a73e2786766bc504cc298c78"

S = "${WORKDIR}/git/scull"
SCRIPTS = "${WORKDIR}"

# Inherit out of tree kernel modules class and provide necessary config
inherit module
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

# Inherit statup script utilities class and provide necessary config
inherit update-rc.d
# Flag this package as one that uses init scripts
INITSCRIPT_PACKAGES="${PN}"
INITSCRIPT_NAME:${PN}= "scull-start-stop" 

do_install:append() {
    install -d ${D}${INIT_D_DIR}
    install -m 0755 ${SCRIPTS}/scull-start-stop   ${D}${INIT_D_DIR}/scull-start-stop
	install -m 0755 ${SCRIPTS}/scull_load         ${D}${INIT_D_DIR}/scull_load
	install -m 0755 ${SCRIPTS}/scull_unload       ${D}${INIT_D_DIR}/scull_unload
}

FILES:${PN} += "\
	${INIT_D_DIR}/scull-start-stop \
	${INIT_D_DIR}/scull_load \
	${INIT_D_DIR}/scull_unload \
"